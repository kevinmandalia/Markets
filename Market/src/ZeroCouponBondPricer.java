import java.util.Date;

public class ZeroCouponBondPricer extends BondPricer {

    public ZeroCouponBondPricer(Bond bond, double initialInvestment) {
        super(bond, initialInvestment);
    }

    @Override
    double calculateBondPrice() {
        int yearsToMaturity = getDate(bond.getMaturity()) - getDate(new Date());

        //check whether we have a yield for the specified tenor
        if(yieldCurve.get(yearsToMaturity) == null){
            System.out.println("No Yield set for " + yearsToMaturity + "yr tenor");
            return 0;
        }

        //set the yield
        bond.setYield(yieldCurve.get(yearsToMaturity));

        return (initialInvestment/Math.pow(bond.getYield()+1,yearsToMaturity));
    }

}
