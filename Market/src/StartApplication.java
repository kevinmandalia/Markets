import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StartApplication {

    public static void main(String[] args){

        System.out.println(requestBondPrice("BTP 2.5% 21/09/22", 10.0, 1000.0, "21/09/2020"));

    }

    private static String requestBondPrice(String name, double coupon, double initialInvestment, String maturityDate){

        SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
        Date date = null;
        try {
            date = fmt.parse(maturityDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //create a new bond to price
        Bond bond = new Bond(name,coupon/100, CouponFrequency.QUARTERLY, date);

        //pass the bond + initial investment
        BondPricer pricer = new BondPricer(bond,initialInvestment); // build the yield curve (Map)

        //Format price to 2dp
        DecimalFormat df = new DecimalFormat(".##");

        return bond.getName()+" Price: £" + df.format(pricer.calculateBondPrice());
    }
}
