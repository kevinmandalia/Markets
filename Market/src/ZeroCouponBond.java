import java.util.Date;

public class ZeroCouponBond extends Bond {

    private static final int COUPON = 0; // No matter how many objects are created, all coupons will be 0

    public ZeroCouponBond(String name, Date maturity, DayCountConvention dayCountConvention){
        super(name,COUPON, CouponFrequency.SEMIANNUAL, maturity, null, dayCountConvention,true);
    }
}
