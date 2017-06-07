import java.util.Date;

public class VanillaBondPricer extends BondPricer {

    public VanillaBondPricer(Bond bond, double initialInvestment) {
        super(bond, initialInvestment);
    }


    double calculateBondPrice() {
        int yearsToMaturity = getDate(bond.getMaturity()) - getDate(new Date());

        //check whether we have a yield for the specified tenor
        if(yieldCurve.get(yearsToMaturity) == null){
            System.out.println("No Yield set for " + yearsToMaturity + "yr tenor");
            return 0;
        }

        //set the yield
        bond.setYield(yieldCurve.get(yearsToMaturity));

        //determine the coupon frequency of the bond
        int frequency;
        switch(bond.getFrequency()){
            case ANNUAL:
                frequency = 1;
                break;
            case SEMIANNUAL:
                frequency = 2;
                break;
            case QUARTERLY:
                frequency = 4;
                break;
            default:
                frequency = 1;
                break;
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

        if(bond.isClean()){
            return price;
        }else{
            price+=calculateInterest();
            return price;
        }
    }


}
