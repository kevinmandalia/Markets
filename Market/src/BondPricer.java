import java.io.FileReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class BondPricer {

    private Bond bond;
    private double initialInvestment;
    private Map<Integer,Double> yieldCurve;

    public BondPricer(Bond bond, double initialInvestment){
        this.bond = bond;
        this.initialInvestment = initialInvestment;

        //Generate the yield curve used for pricing bonds
        populateYieldCurve();
    }

    private void populateYieldCurve() {

        yieldCurve = new HashMap<>();

        //read yield curve file
        try {
            Scanner scanner = new Scanner(new FileReader("/Users/kevinmandalia/Desktop/YieldCurve.txt"));
            while (scanner.hasNext()) {
                String[] vals = scanner.nextLine().split(",");
                yieldCurve.put(Integer.parseInt(vals[0]), Double.parseDouble(vals[1]));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public double calculateBondPrice(){

        int yearsToMaturity = getDate(bond.getMaturity()) - getDate(new Date());

        //check whether we have a yield for the specified tenor
        if(yieldCurve.get(yearsToMaturity) == null){
            System.out.println("No Yield set for " + yearsToMaturity + "yr tenor");
            return 0;
        }

        //set the yield
        bond.setYield(yieldCurve.get(yearsToMaturity));


        //determine the coupon frequency of the bond
        int frequency = 0;

        if(bond.getFrequency() == CouponFrequency.SEMIANNUAL){
            frequency = 2;
        }else if(bond.getFrequency() == CouponFrequency.QUARTERLY){
            frequency = 4;
        }else{
            frequency = 1; //For annual payments or any other undefined type of payment (default)
        }

        //re-calculate the coupon and yield with respect to the coupon frequency
        double coupon = bond.getCoupon()/frequency;
        double yield = bond.getYield()/frequency;

        int paymentNumber = 1;
        double price = 0;

        while(paymentNumber != (yearsToMaturity*frequency)+1){

            double couponPayment = initialInvestment * coupon;

            if(paymentNumber == (yearsToMaturity*frequency)){
                price += calculatePVAtT(initialInvestment + couponPayment, yield,paymentNumber);
            }else {
                price += calculatePVAtT(couponPayment,yield,paymentNumber);
            }
            paymentNumber++;
        }

        return price;
    }

    private int getDate(Date date){
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        return Integer.parseInt(df.format(date).substring(6));
    }

    private double calculatePVAtT(double couponPayment, double yield, int paymentNumber){
        System.out.println("Payment Number(" + paymentNumber + ") with yield " + yield + " : £" + couponPayment/Math.pow(yield+1,paymentNumber));
        return couponPayment/Math.pow(yield+1,paymentNumber);
    }
}
