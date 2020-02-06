import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;
import java.util.*;

public class NewsTest {

    @Test
    public void name() {
            System.setProperty("webdriver.chrome.driver","/home/admin1/Desktop/JavaDemo/Drivers/chromedriver");
            WebDriver driver=new ChromeDriver();
            List<String> newsHeading = new ArrayList<String>();
            List<String> newsPoints = new ArrayList<String>();
            Map<String,Integer> newsMap = new HashMap<>();
            driver.get("https://news.ycombinator.com");
            List<WebElement> newsElements = driver.findElements(By.cssSelector("a.storylink"));
            List<WebElement> scorePoints = driver.findElements(By.cssSelector("tr td span.score"));
            for (WebElement webElement : newsElements)
            {

                System.out.println(webElement.getText());
                newsHeading.add( webElement.getText());
            }

        for (WebElement webElement : scorePoints)
        {

            System.out.println(webElement.getText().substring(0,webElement.getText().length()-7));
            newsHeading.add( webElement.getText().substring(0,webElement.getText().length()-7));
        }

         for(int i=0;i<newsHeading.size();i++)
         {
             System.out.println(newsHeading.get(i));
             System.out.println(Integer.parseInt(newsPoints.get(i)));
             newsMap.put(newsHeading.get(i),Integer.parseInt(newsPoints.get(i)));
         }

        List<String> listOfWords = listOfWords(newsHeading);
        findWords(listOfWords,newsMap);


    }
    static List<String> listOfWords(List<String> news)
    {
        List<String> listOfWords = new ArrayList<String>();
        for (String s : news)
        {
            String[] arrOfString = s.split(" ");
            List<String> l1= Arrays.asList(arrOfString);
            listOfWords.addAll(l1);
        }
        System.out.println(listOfWords);
        return listOfWords;
    }

    static void findWords(List<String> arr,Map<String,Integer> newsMap)
    {
        for (int i = 0; i < arr.size(); i++) {

            if (newsMap.containsKey(arr.get(i))) {
                newsMap.put(arr.get(i), newsMap.get(arr.get(i)) + 1);
            }
            else {
                newsMap.put(arr.get(i), 1);
            }
        }
        String highestValue = getHighest(newsMap);
        System.out.println("Highest value is "+highestValue);
    }

    static String getHighest(Map<String,Integer> newsMap)
    {
        Set<Map.Entry<String, Integer> > set = newsMap.entrySet();
        String key = "";
        int value =0;

        for (Map.Entry<String, Integer> me : set) {
            if (me.getValue() > value) {
                value = me.getValue();
                key = me.getKey();
            }
        }
        System.out.println("------------------------"+key);
        return key;
    }
}
