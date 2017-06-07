import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StartApplication {

    public static void main(String[] args){

        System.out.println(requestBondPrice(
                "BTP 2.5% 21/09/22",
                10,
                1000.0,
                "21/09/2027",
                "01/02/2018",
                DayCountConvention.ACT360));
    }

    private static String requestBondPrice(String name, double coupon, double initialInvestment, String maturityDate, String nextPaymentDate, DayCountConvention dayCountConvention){

        SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
        Date maturity = null;
        Date nextPayment = null;
        try {
            maturity = fmt.parse(maturityDate);
            nextPayment = fmt.parse(nextPaymentDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Bond bond = null;
        BondPricer pricer = null;

        //Format price to 2dp
        DecimalFormat df = new DecimalFormat(".##");

        if(coupon != 0){ //Vanilla Bond
            bond = new VanillaBond(name, coupon/100, CouponFrequency.ANNUAL, maturity, nextPayment, dayCountConvention, true);
            pricer = new VanillaBondPricer(bond,initialInvestment);

            if(bond.isClean()){
                return "Clean Price: £" + df.format(pricer.calculateBondPrice());
            }else{
                return "Dirty Price: £" + df.format(pricer.calculateBondPrice());
            }

        }else{//Zero Coupon Bond
            bond = new ZeroCouponBond(name,maturity, dayCountConvention);
            pricer = new ZeroCouponBondPricer(bond,initialInvestment);
            return "£" + df.format(pricer.calculateBondPrice());
        }
    }
}
