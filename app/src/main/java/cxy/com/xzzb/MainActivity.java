package cxy.com.xzzb;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cxy.com.validate.IValidateResult;
import cxy.com.validate.SimpleValidateResult;
import cxy.com.validate.Validate;
import cxy.com.validate.ValidateAnimation;
import cxy.com.validate.annotation.Index;
import cxy.com.validate.annotation.MinLength;
import cxy.com.validate.annotation.NotNull;
import cxy.com.validate.annotation.Shield;


/**
 * https://github.com/LongMaoC/validateui
 *
 * @Shield 注解在实际中的使用demo
 * <p>
 * 注意 ： 1. Shield 注解 放在想屏蔽的控件上
 * 2. 校验时，调用check()方法，注意传递参数
 */
public class MainActivity extends AppCompatActivity {

    @Shield
    @Index(1)
    @NotNull(msg = "姓名不能为空")
    @Bind(R.id.et_answer)
    EditText etAnswer;

    @Shield
    @Index(2)
    @NotNull(msg = "民族不能为空")
    @Bind(R.id.et_new_pwd1)
    EditText etNewPwd1;

    @Shield
    @Index(3)
    @MinLength(length =18, msg = "身份证号必须18位")
    @NotNull(msg = "身份证号不能为空")
    @Bind(R.id.et_new_pwd2)
    EditText etNewPwd2;

    @Shield
    @Index(7)
    @NotNull(msg = "地址不能为空")
    @Bind(R.id.adds)
    EditText addss;

    @Index(4)
    @Bind(R.id.radioButton1)
    RadioButton rb1;

    @Index(5)
    @Bind(R.id.radioButton2)
    RadioButton rb2;

    @Index(6)
    @Bind(R.id.sfz)
    TextView sfz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shield_demo);
        ButterKnife.bind(this);
        Validate.reg(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Validate.unreg(this);
    }

    @OnClick({R.id.radioButton1,R.id.radioButton2})
    public void onViewClickeds(View view) {
        switch (view.getId()) {
            case R.id.radioButton1:
                rb2.setChecked(false);
                break;
            case R.id.radioButton2:
                rb1.setChecked(false);
                break;
        }
    }

    @OnClick( R.id.btn_submit)
    public void onViewClicked(View view) {
//        switch (view.getId()) {
//            case R.id.btn_submit:
//                submit();
//                break;
//        }
        submit();
    }

    private void submit() {
        Validate.check(this, new SimpleValidateResult() {
            @Override
            public void onValidateSuccess() {
                Toast.makeText(MainActivity.this, etAnswer.getText()+"\n保存成功", Toast.LENGTH_SHORT).show();
                sfz.setText(getBirthday(etNewPwd2.getText().toString()));
            }
        });
    }
    public static String getBirthday(String id){
        String year = null;
        String month = null;
        String day = null;
        //正则匹配身份证号是否是正确的，15位或者17位数字+数字/x/X
        if (id.matches("^\\d{15}|\\d{17}[\\dxX]$")) {
            year = id.substring(6, 10);
            month = id.substring(10,12);
            day = id.substring(12,14);
        }else {
            System.out.println("身份证号码不匹配！");
            return null;
        }
        return year + "-" + month + "-" + day;
    }


    private void countDownClick() {
        Validate.check(this, true, new IValidateResult() {
            @Override
            public void onValidateSuccess() {
                Toast.makeText(MainActivity.this, "发送验证码按钮，回调校验成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onValidateError(String msg, View view) {
                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
            }

            @Override
            public Animation onValidateErrorAnno() {
                return ValidateAnimation.horizontalTranslate();
            }
        });
    }
}
