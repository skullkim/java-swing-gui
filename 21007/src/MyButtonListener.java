/*
 * Classname: MyButtonListener
 * Version: 1.0
 * Date: 2021-05-17
 * Copyright: MIT
 */

//버튼 리스너
public class MyButtonListener implements ButtonListener{
    MyButton my_button;//해당 리스너와 연결된 버튼
    //생성자
    public MyButtonListener(){}

    //해당 리스너와 연결될 버튼을 세팅
    public void setMyButton(MyButton _my_button){
        this.my_button = _my_button;
    }

    //버튼이 클릭되면 해당 버튼이 어느것인지 알려준다
    @Override
    public void actionPerformed() {
        DrawFigure.setCurrClicked(my_button.getName());
    }
}
