public class Power {

    int power=1;
    public int calculatePower(int number, int powerValue)
    {
        for (int i=1;i<=powerValue;i++)
        {
            power=power*number;

        }
        return power;
    }

}
