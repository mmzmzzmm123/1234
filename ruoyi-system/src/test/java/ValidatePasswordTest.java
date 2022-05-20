import com.ruoyi.system.domain.PasswordConfig;
import com.ruoyi.system.service.ISysPasswordService;
import com.ruoyi.system.service.impl.SysPasswordServiceImpl;
import org.junit.Before;
import org.junit.Test;

public class ValidatePasswordTest {

    ISysPasswordService service = null;
    PasswordConfig passwordConfig = null;
    @Before
    public void initSysPasswordService()
    {
        service = new SysPasswordServiceImpl();
        passwordConfig = new PasswordConfig();
        passwordConfig.setMaxFailedLoginAttempts(5);
        passwordConfig.setPasswordExpirationPeriodDays(90);
        passwordConfig.setUserLockPeriod(10);
        passwordConfig.setMaxFailedLoginAttempts(5);
        passwordConfig.setMaximumLength(20);
        passwordConfig.setMinimumLength(5);
        passwordConfig.setMinimumDigits(1);
        passwordConfig.setMinimumSpecialCharacters(1);
        passwordConfig.setMinimumLowercaseLetters(1);
        passwordConfig.setMinimumUppercaseLetters(1);
        service.setPasswordConfig(passwordConfig);
    }

    /**
     * 满足策略要求用例
     */
    @Test
    public void testValidedByConfig(){
        ISysPasswordService serviceNew = new SysPasswordServiceImpl();
        serviceNew.validatePasswordRule("Hello@123",passwordConfig);
    }

    /**
     * 不满足策略要求用例
     */
    @Test
    public void testInvalidByConfig(){
        ISysPasswordService serviceNew = new SysPasswordServiceImpl();
        serviceNew.validatePasswordRule("Hello123",passwordConfig);
    }

    /**
     * 满足策略要求用例
     */
    @Test
    public void testValided(){
        service.validatePasswordRule("Hello@123");
    }

    /**
     * 不满足策略要求用例
     */
    @Test
    public void testInValid(){
        service.validatePasswordRule("Hello123");

    }
}