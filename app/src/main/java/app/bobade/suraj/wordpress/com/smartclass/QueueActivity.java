package app.bobade.suraj.wordpress.com.smartclass;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.LinkedList;

public class QueueActivity extends AppCompatActivity
{
    Spinner select_operation_spinner;
    EditText enter_element_editText;
    LinearLayout result_box;
    TextView index,element,address,paddress, readTheory;
    View curr_node, prev_node;
    int operationChoice,input,iindex,iaddress=1000;
    LinkedList linkedList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_queue);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        result_box = (LinearLayout) findViewById(R.id.result_box);

        enter_element_editText = (EditText) findViewById(R.id.enter_element_editText);

        linkedList = new LinkedList();

        ArrayAdapter spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.queueoperations, android.R.layout.simple_spinner_dropdown_item);
        select_operation_spinner = (Spinner) findViewById(R.id.select_operation_spinner);
        select_operation_spinner.setAdapter(spinnerAdapter);
        select_operation_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                operationChoice = position;
                if (position == 0)
                    enter_element_editText.setEnabled(true);
                else {
                    enter_element_editText.setEnabled(false);
                    dequeue();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                enter_element_editText.setEnabled(false);
            }
        });


        enter_element_editText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.setFocusable(true);
                v.setFocusableInTouchMode(true);
                return false;
            }
        });
        enter_element_editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    input = Integer.parseInt(enter_element_editText.getText().toString());
                    operate(input);
                    handled = true;
                }
                return handled;
            }
        });


        readTheory = (TextView) findViewById(R.id.read_theory_text);
        readTheory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), QueueTheoryActivity.class);
                startActivity(intent);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(QueueActivity.this, QueueActivity.class);
                finish();
                startActivity(intent);
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    public void operate(int input)
    {
//        Toast.makeText(getBaseContext(),"Operate",Toast.LENGTH_SHORT).show();
        switch (operationChoice)
        {
            case 0:
                enqueue(input);
                break;
        }
    }

    public void enqueue(int input)
    {
        LayoutInflater layoutInflater=LayoutInflater.from(this);
        curr_node=layoutInflater.inflate(R.layout.node, null);

        while(true)
        {
            iaddress = (int) (Math.random() * 10 + iaddress+8);
            if(!linkedList.contains(address))
            {
                for(int i=0;i<8;i++)
                {
                    linkedList.add(iaddress + i);
                }
                break;
            }
        }
        enter_element_editText.setText("");

        index=(TextView)curr_node.findViewById(R.id.index);
        index.setText(""+iindex);

        element=(TextView)curr_node.findViewById(R.id.element);
        element.setText("" + input);

        address=(TextView)curr_node.findViewById(R.id.address);
        address.setText("" + iaddress);

        prev_node=result_box.getChildAt(iindex-1);
        if(iindex++>0)
        {
            paddress=(TextView)prev_node.findViewById(R.id.next);
            paddress.setText(""+iaddress);
        }
        result_box.addView(curr_node);
    }

    public void dequeue()
    {
        result_box = (LinearLayout) findViewById(R.id.result_box);
        try{
            curr_node = result_box.getChildAt(0);
            result_box.removeViewAt(0);
            iindex--;
            for (int i = 0; i < iindex; i++)
            {
                curr_node = result_box.getChildAt(i);
                index = (TextView) curr_node.findViewById(R.id.index);
                index.setText("" + i);
            }
            enter_element_editText.setEnabled(true);
            select_operation_spinner.setSelection(0);
        }
        catch(Exception e){
            Toast.makeText(getBaseContext(), "operation NOT allowed..!", Toast.LENGTH_SHORT).show();
        }
    }
}
