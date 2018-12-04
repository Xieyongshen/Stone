package application;

public class NumToken extends Token{
    private double value;
    protected NumToken(int line, double v) {
        super(line);
        value = v;
    }

    @Override
    public int getType() { return 1; }

    @Override
    public double getNumber() { return value; }

    @Override
    public String getText() {
        if(value % 1.0 == 0){
            return String.valueOf((int)value);
        }
        return Double.toString(value);
    }
}
