import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class Interface extends JFrame implements ActionListener
{
    JButton parser = new JButton("分词");
    JLabel  sentence = new JLabel("表达式：");
    JLabel result = new JLabel("结果：");
    JTextField JSentence= new JTextField(10);
    JTextField JResult= new JTextField(10);

    public Interface()
    {
        JPanel jp = new JPanel();
        jp.setLayout(new GridLayout(3,2));  //3行2列的面板jp（网格布局）

        sentence.setHorizontalAlignment(SwingConstants.RIGHT);  //设置该组件的对齐方式为向右对齐
        result.setHorizontalAlignment(SwingConstants.RIGHT);


        jp.add(sentence);
        jp.add(result);
        jp.add(JSentence);
        jp.add(JResult);
        jp.add(parser);
        parser.addActionListener(this);

        this.add(jp,BorderLayout.CENTER);	//将整块面板定义在中间

        this.setTitle("算术表达式");
        this.setLocation(500,300);	//设置初始位置
        this.pack();  		//表示随着面板自动调整大小
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public void actionPerformed(ActionEvent e)  
    {

            String sentence = JSentence.getText();
            Parser parser = new Parser(0,-1,sentence);
            ArrayList<TokenInformation> tokenInformations = null;
            try{
                tokenInformations = parser.parser();
            }catch (Exception e1){
                e1.printStackTrace();
            }
            String result = "";
            for(TokenInformation tokenInformation:tokenInformations){
                result+=tokenInformation.toString();
            }
            JResult.setText(result);
    }
    public static void main(String[] args)
    {
        JFrame.setDefaultLookAndFeelDecorated(true);
        new Interface();
    }
}


