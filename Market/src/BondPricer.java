import java.io.FileReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

abstract class BondPricer {

    protected Bond bond;
    protected double initialInvestment;
    protected Map<Integer,Double> yieldCurve;

    public BondPricer(Bond bond, double initialInvestment){
        this.bond = bond;
        this.initialInvestment = initialInvestment;

        //Generate the yield curve used for pricing bonds
        populateYieldCurve();
    }


    abstract double calculateBondPrice();

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

    protected int getDate(Date date){
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        return Integer.parseInt(df.format(date).substring(6));
    }

    protected double calculatePVAtT(double couponPayment, double yield, int paymentNumber){
        System.out.println("Payment Number(" + paymentNumber + ") with yield " + yield + " : £" + couponPayment/Math.pow(yield+1,paymentNumber));
        return couponPayment/Math.pow(yield+1,paymentNumber);
    }

    protected double calculateInterest(){

        Date today = new Date();
        Date nextCouponPaymentDate = bond.getNextPaymentDate();
        Date lastCouponPaymentDate;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(nextCouponPaymentDate);

        switch(bond.getFrequency()){
            case ANNUAL:
                calendar.add(Calendar.MONTH,-12);
                break;
            case SEMIANNUAL:
                calendar.add(Calendar.MONTH,-6);
                break;
            case QUARTERLY:
                calendar.add(Calendar.MONTH,-3);
                break;
            default:
                System.out.println("Unknown Coupon Frequency specified ".concat(bond.getFrequency().toString()));
                break;
        }

        lastCouponPaymentDate = calendar.getTime();

        long daysToPayInterestOn = TimeUnit.MILLISECONDS.toDays(today.getTime() - lastCouponPaymentDate.getTime());
        long totalDaysInCouponPeriod = TimeUnit.MILLISECONDS.toDays(nextCouponPaymentDate.getTime() - lastCouponPaymentDate.getTime());

        double couponPayment = bond.getCoupon() * initialInvestment;
        double result = 0;

        switch (bond.getDayCountConvention()){
            case ACT365:
                result = ((double) daysToPayInterestOn/365) * couponPayment;
                break;
            case ACT360:
                result = ((double) daysToPayInterestOn/360) * couponPayment;
                break;
            case ACTACT:
                result = ((double) daysToPayInterestOn/totalDaysInCouponPeriod) * couponPayment;
                break;
            default:
                System.out.println("Unknown Day Count Convention specified ".concat(bond.getDayCountConvention().toString()));
                break;
        }

        System.out.println(daysToPayInterestOn + " Days Of Interest: £"+ result);

        return result;

    }
}
