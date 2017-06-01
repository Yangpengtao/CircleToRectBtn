package cn.com.oomall.kktown.activity.seller;



/**
 * 登录页面
 * Yang pengtao
 */
public class LoginActivity extends BaseSellerActivity implements CompoundButton.OnCheckedChangeListener {

    private final String TAG = "----LoginActivity-->>";
    private LoginBtn businessBtn, buyerBtn, currBtn;
    private LinearLayout businessLay, buyerLay, accountLay, bottomLay;
    private CheckBox cbShow;
    private ImageButton btnChange;
    private TextView tvShiyong;
    private EditText etName, etPass;
    private int businessLayY, buyerLayY, btnChangeY;
    private ScaleAnimation scaleA;
    private ObjectAnimator rotateAnimation;

    private UserPresenter presenter;
    /*定义当前是卖家登录，还是买家，true是买家，false是卖家*/
    private boolean isBuyer = true;
    private String username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViewById();
        initView();
        setListener();
    }

    private void initView() {
        scaleA = new ScaleAnimation(0, 1, 0, 1, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 1f);
        scaleA.setDuration(500);
        scaleA.setFillAfter(true);
        etName.setText(SpUtil.getInstance().getLoginAccount(LoginActivity.this));
    }

    private void setListener() {
        businessBtn.setOnClickListener(clickListener);
        buyerBtn.setOnClickListener(clickListener);
        btnChange.setOnClickListener(clickListener);
        tvShiyong.setOnClickListener(clickListener);
        cbShow.setOnCheckedChangeListener(this);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (currBtn != null) {
            if (!currBtn.getIsfir()) {
                if (currBtn.getMeasuredWidth() > currBtn.getMaxWidth()) {
                    currBtn.postInvalidate();
                }

            }
        }
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        int[] position = new int[2];
        businessLay.getLocationInWindow(position);
        businessLayY = position[1];

        position = new int[2];
        buyerLay.getLocationInWindow(position);
        buyerLayY = position[1];

        position = new int[2];
        btnChange.getLocationInWindow(position);
        btnChangeY = position[1];
    }

    public void findViewById() {
        buyerBtn = (LoginBtn) findViewById(R.id.buyerBtn);
        businessBtn = (LoginBtn) findViewById(R.id.businessBtn);
        businessLay = (LinearLayout) findViewById(R.id.businessLay);
        buyerLay = (LinearLayout) findViewById(R.id.buyerLay);
        accountLay = (LinearLayout) findViewById(R.id.accountLay);
        bottomLay = (LinearLayout) findViewById(R.id.bottomLay);
        cbShow = (CheckBox) findViewById(R.id.cbShow);
        btnChange = (ImageButton) findViewById(R.id.btnChange);
        tvShiyong = (TextView) findViewById(R.id.tvShiyong);
        etName = (EditText) findViewById(R.id.etName);
        etPass = (EditText) findViewById(R.id.etPass);
        presenter = new UserPresenter();
    }

    /**
     * 商家登录按钮动画
     */
    private Animator.AnimatorListener businessAnimatorListener = new Animator.AnimatorListener() {
        @Override
        public void onAnimationStart(Animator animator) {

        }

        @Override
        public void onAnimationEnd(Animator animator) {
            btnChange.setVisibility(View.VISIBLE);
            buyerLay.setVisibility(View.GONE);
            bottomLay.setVisibility(View.VISIBLE);
            accountLay.setVisibility(View.VISIBLE);
            accountLay.startAnimation(scaleA);
        }

        @Override
        public void onAnimationCancel(Animator animator) {

        }

        @Override
        public void onAnimationRepeat(Animator animator) {

        }
    };


    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int id = view.getId();
            switch (id) {
                case R.id.businessBtn:
                    businessBtnClick();
                    break;
                case R.id.buyerBtn:
                    buyerBtnClick();
                    break;
                case R.id.btnChange:
                    btnChangeClick();
                    break;
                case R.id.tvShiyong:
                    startActivity(cn.com.oomall.kktown.activity.seller.HomeActivity.class);
                    break;
            }
        }
    };

    private void btnChangeClick() {
        rotateAnimation.setDuration(300);
        if (isBuyer) {
            isBuyer = false;
            //如果当前是买家登录，变换成卖家登录
            currBtn.setText("卖家登录");
            currBtn.postInvalidate();

        } else {
            isBuyer = true;
            //如果当前是卖家登录，变换成买家登录
            currBtn.setText("买家登录");
            currBtn.postInvalidate();
        }
        rotateAnimation.start();
    }

    private void buyerBtnClick() {
        currBtn = buyerBtn;
        rotateAnimation = ObjectAnimator.ofFloat(buyerBtn, "rotationX", 180, 360);
        if (buyerBtn.getIsfir()) {
            isBuyer = true;
            bottomLay.setVisibility(View.VISIBLE);
            btnChange.setVisibility(View.VISIBLE);
            accountLay.setVisibility(View.VISIBLE);
            accountLay.startAnimation(scaleA);
            businessLay.setVisibility(View.INVISIBLE);
            businessLay.setVisibility(View.GONE);
            buyerBtn.setIsfir(false);
            buyerBtn.postInvalidate();
            //因为圆形变圆角矩形的时候，是向下走的。所以要向上移动位置
            //图片原始为250像素
            int endHeight = ScreenUtils.convertDpToPixel(LoginActivity.this, 30);
            ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(buyerLay, "translationY", 0, -endHeight).setDuration(600);
            objectAnimator.start();
        } else {
            startActivity(new Intent(this,HomeActivity.class));
            login();
        }
    }

    private void businessBtnClick() {
        currBtn = businessBtn;
        rotateAnimation = ObjectAnimator.ofFloat(businessBtn, "rotationX", 180, 360);

        if (businessBtn.getIsfir()) {
            isBuyer = false;
            buyerLay.setVisibility(View.INVISIBLE);
            //卖家登录
            businessBtn.setIsfir(false);
            businessBtn.postInvalidate();
            ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(businessLay, "translationY", 0, (buyerLayY - businessLayY)).setDuration(1000);
            objectAnimator.addListener(businessAnimatorListener);
            objectAnimator.start();
        } else {
            login();
        }
    }


    private void login() {
        if (!checked()) return;
        if (isBuyer) {
            ToastUtil.show(LoginActivity.this, "从这里实现买家登录");
        } else {
            ToastUtil.show(LoginActivity.this, "从这里实现卖家登录");

            getToken();
        }
    }

    private boolean checked() {
        if (etName.getText().length() == 0 || etPass.getText().length() == 0) {
            ToastUtil.show(LoginActivity.this, "帐号和密码不能为空！");
            return false;
        }
        username = etName.getText().toString();
        password = etPass.getText().toString();
        return true;
    }


    private void getToken() {
        presenter.getToken(this, true, username, password, new Action1<TokenBean>() {
            @Override
            public void call(TokenBean tokenBean) {
                LogPrinter.e(TAG, "------" + tokenBean.data.token);
                SpUtil.getInstance().saveToken(LoginActivity.this, tokenBean.data.token);
                SpUtil.getInstance().saveLoginAccount(LoginActivity.this, username);
                sellerLogin();
            }
        });
    }

    private void sellerLogin() {
        LogPrinter.e(TAG, "------进来" );

        presenter.login(this, true, new Action1<UserBean>() {
            @Override
            public void call(UserBean userBean) {
                LogPrinter.e(TAG, "------" + userBean);
                startActivity(cn.com.oomall.kktown.activity.seller.HomeActivity.class);
                finish();
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
//        scaleA.cancel();
//        rotateAnimation.cancel();
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (b) {
            etPass.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        } else {
            etPass.setInputType(InputType.TYPE_CLASS_TEXT
                    | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }
    }
}
