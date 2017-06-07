import java.util.Date;

public class VanillaBond extends Bond {

    public VanillaBond(String name, double coupon, CouponFrequency frequency, Date maturity, Date nextPaymentDate, DayCountConvention dayCountConvention,
                       boolean isClean) {
        super(name, coupon, frequency, maturity, nextPaymentDate, dayCountConvention, isClean);
    }

}
