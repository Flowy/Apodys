package sample.tupper;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.math.BigInteger;

import javax.swing.JPanel;

public class Paint extends JPanel {

    private static final long serialVersionUID = 1L;

    public static BigInteger K = new BigInteger("583631134709551584667739945424919377251444040438346973239489650545029726844978132649268609475789164538961571169188616607564500080614277074902703616367980617783905961535017292969652203012447612282789863021655245959755293261430307388698914951827055087576725478492772242054051844851750515285902845270234613068719891338993443528450258191266189586063306176856893662794863082341495397103343534589406609408");
    public static boolean set_buffer(float xAxis, float yAxis) {
        BigInteger y = new BigInteger("" + (int) yAxis); y = y.add(K);
        BigInteger x = new BigInteger("" + (int) xAxis);

        //meat of the actual formula
        if(0.5 < y.divideAndRemainder(BigInteger.valueOf(17))[0].divideAndRemainder(BigInteger.valueOf(2).pow(BigInteger.valueOf(17).multiply(x).add(y.mod(BigInteger.valueOf(17))).intValue()))[0].mod(BigInteger.valueOf(2)).doubleValue()) return true;
        else return false;
    }

    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.BLACK);

        int _j;
        for(int i = 0; i < Tupper.yRes; i++) {
            for(int j = 0; j < Tupper.xRes; j++) {
                if(set_buffer(((float) j)/5L, ((float) i)/5L))
                    //first argument prevents image from appearing reversed
                    g2.drawLine( (_j = Tupper.xRes - j), i, _j, i);
            }
        }

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

}