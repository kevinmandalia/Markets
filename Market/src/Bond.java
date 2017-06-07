import java.util.Date;

public class Bond {

    private String name;
    private double coupon;
    private Date maturity;
    private double yield;
    private CouponFrequency frequency;
    private DayCountConvention dayCountConvention;
    private Date nextPaymentDate;
    private boolean isClean;

    public Bond(String name, double coupon, CouponFrequency frequency, Date maturity, Date nextPaymentDate, DayCountConvention dayCountConvention,
                boolean isClean){
        this.name = name;
        this.coupon = coupon;
        this.maturity = maturity;
        this.frequency = frequency;
        this.dayCountConvention = dayCountConvention;
        this.nextPaymentDate = nextPaymentDate;
        this.isClean = isClean;
        yield = 0.0;
    }

    public String getName() {
        return name;
    }

    public double getCoupon() {
        return coupon;
    }

    public Date getMaturity() {
        return maturity;
    }

    public double getYield() {
        return yield;
    }

    public void setYield(double yield){
        this.yield = yield;
    }

    public CouponFrequency getFrequency() {
        return frequency;
    }

    public DayCountConvention getDayCountConvention() {
        return dayCountConvention;
    }

    public Date getNextPaymentDate() {
        return nextPaymentDate;
    }

    public boolean isClean() {
        return isClean;
    }

    public void setClean(boolean clean) {
        isClean = clean;
    }
}
