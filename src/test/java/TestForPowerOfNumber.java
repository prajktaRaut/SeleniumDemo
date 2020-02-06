import org.testng.Assert;
import org.testng.annotations.Test;

public class TestForPowerOfNumber {

    @Test
    public void givenMethodToCalculatePowerOfPositiveNumber() {

        Power power = new Power();
        int result = power.calculatePower(2, 3);
        Assert.assertEquals(8,result);

    }

    @Test
    public void givenMethodToCalculatePowerOfNegativeNumber() {

        Power power = new Power();
        int result = power.calculatePower(2, 0);
        Assert.assertEquals(1,result);
    }
}
