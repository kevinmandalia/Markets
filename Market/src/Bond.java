import java.util.Date;

public class Bond {

    private String name;
    private double coupon;
    private Date maturity;
    private double yield;
    private CouponFrequency frequency;

    public Bond(String name, double coupon, CouponFrequency frequency, Date maturity){
        this.name = name;
        this.coupon = coupon;
        this.maturity = maturity;
        this.frequency = frequency;
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
}
