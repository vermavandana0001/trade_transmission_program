package trade.transmission;

import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

class TradeTransmissionFlowTest {

    TradeTransmissionFlow tradeTransmissionFlow = new TradeTransmissionFlow();
    Date currentDate = Calendar.getInstance().getTime();
    SimpleDateFormat sd = new SimpleDateFormat("dd/MM/yyyy");


    @Test
    void addTradeDetailTest() throws Exception {
        Date maturityDate = sd.parse("20/05/2021");
        TradeDetail t1 = new TradeDetail("T1", 1, "CP-1", "B1", maturityDate, currentDate, 'N');
        tradeTransmissionFlow.addTradeDetail(t1);
    }

}
