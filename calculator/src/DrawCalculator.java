import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class DrawCalculator extends JFrame{
    private JTextField number_field;
    private ArrayList<JButton> btns;//버튼을 저장하는 리스트
    private MyActionListener action_listener;//버튼을 눌르는 이벤트 리스너
    private String factor = "";//입력받은 수
    private String operator = "";//입력받은 연산자
    private double cal_result = 0.0;//최종 결과
    private final int FIRST_X = 50;//버튼의 첫 X좌표
    private final int FIRST_Y = 70;//버튼의 첫 Y좌표
    private final int HEIGHT = 30;//버튼의 높으
    private final int WIDTH = 70;//버튼의 너비
    private final String []OPERATORS = {"", "+", "-", "*", "/", "=", "."};//연산자

    public DrawCalculator(){
        super();
        btns = new ArrayList<>();
        action_listener = new MyActionListener();
        super.setSize(500, 400);
        super.setDefaultCloseOperation(EXIT_ON_CLOSE);
        initComponent();
        setLayout(null);
        setVisible(true);
    }

    class MyActionListener implements ActionListener{//버튼을 누르는 이벤트에대한 리스너 객체
        @Override
        public void actionPerformed(ActionEvent e) {
            String pushed = e.getActionCommand();
            //누른게 수이면 해당 수를 기록하고 화면에 보여준다
            if(Character.isDigit(pushed.charAt(0)) || pushed.equals(".")){
                factor += pushed;
                number_field.setText(factor);
            }
            //각 연산에 대한 처리
            else if(pushed.equals("+")){
                recordExpression("+");
            }
            else if(pushed.equals("-")){
                if(factor.isEmpty() == true){
                    factor += "-";
                }
                else{
                    recordExpression("-");
                    factor="";
                }
            }
            else if(pushed.equals("*")){
                recordExpression("*");
            }
            else if(pushed.equals("/")){
                recordExpression("/");
            }
            else if(pushed.equals("=")){
                CalculateNumber();
                number_field.setText(Double.toString(cal_result));
                operator = "";
                factor = "";
                cal_result = 0.0;
            }
        }
    }
    //연산자 버튼을 눌렀을때 연산을 하고 따라오는 연산을 하기 위한 메서드
    private void recordExpression(String op){
        if(operator.length() != 0){//이전에 누른 연산자가 있으면
            CalculateNumber();//지금까지의 연산을 한다
            operator = op;
        }
        else if(factor.isEmpty() == false){//누른 수가 있고 이전에 누른 연산자가 없을때
            cal_result += Double.parseDouble(factor);
            operator = op;
        }
        factor = "";
    }

    private void CalculateNumber(){//실제 연산을 한다
        switch (operator){
            case "+":
                cal_result += Double.parseDouble(factor);
                break;
            case "-":
                cal_result -= Double.parseDouble(factor);
                break;
            case "*":
                cal_result *= Double.parseDouble(factor);
                break;
            case "/":
                cal_result /= Double.parseDouble(factor);
                break;
        }
    }
    public void initComponent(){//버튼 초기화, 맨처음 실행되었을때 실행된다
        number_field = new JTextField();
        number_field.setBounds(50, 20, 350, 50);
        add(number_field);
        for(int n1 = 1, btn_num = 0; n1 <= 3; n1++){
            for(int n2 = 1; n2 <= 3; n2++){
                JButton num_btn = new JButton(Integer.toString(btn_num++));
                num_btn.setBounds(FIRST_X + WIDTH * (n2 -1), FIRST_Y + HEIGHT* (n1 - 1), WIDTH, HEIGHT);
                num_btn.addActionListener(action_listener);
                add(num_btn);
                btns.add(num_btn);
            }
        }
        for(int n1 = 1; n1 <= 3; n1++){
            JButton op_btn = new JButton(OPERATORS[n1]);
            op_btn.setBounds(FIRST_X + WIDTH * 3, FIRST_Y + HEIGHT * (n1 - 1), WIDTH, HEIGHT);
            op_btn.addActionListener(action_listener);
            add(op_btn);
            btns.add(op_btn);
        }
        for(int n1 = 4; n1 <= 6; n1++){
            JButton op_btn = new JButton(OPERATORS[n1]);
            op_btn.setBounds(FIRST_X + WIDTH * 4, FIRST_Y + HEIGHT * (n1 - 4), WIDTH, HEIGHT);
            op_btn.addActionListener(action_listener);
            add(op_btn);
            btns.add(op_btn);
        }
    }

    @Override
    public void paint(Graphics _g){
        super.paint(_g);
    }
}
