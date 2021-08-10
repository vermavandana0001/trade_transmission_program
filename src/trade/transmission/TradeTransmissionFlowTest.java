package trade.transmission;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class TradeTransmissionFlowTest {

    TradeTransmissionFlow tradeTransmissionFlow = new TradeTransmissionFlow();
    Date currentDate = Calendar.getInstance().getTime();
    SimpleDateFormat sd = new SimpleDateFormat("dd/MM/yyyy");

    @BeforeEach
    void clearStore() {
        tradeTransmissionFlow.tradeDetailsMap.clear();
    }

    //Add trade with correct details
    @Test
    void addTradeDetailSuccessTest() throws Exception {
        Date maturityDate = sd.parse(sd.format(getFutureDate()));
        TradeDetail trade = new TradeDetail("T1", 1, "CP-1", "B1", maturityDate, currentDate, 'N');
        tradeTransmissionFlow.addTradeDetail(trade);
        assertEquals(trade, tradeTransmissionFlow.getTradeDetail("T1"));
    }

    @Test
    void addTradeWithHigherVersionSuccessTest() throws Exception {

        Date maturityDate = sd.parse(sd.format(getFutureDate()));
        TradeDetail trade2 = new TradeDetail("T2", 2, "CP-2", "B1", maturityDate, currentDate, 'N');
        tradeTransmissionFlow.addTradeDetail(trade2);

        TradeDetail trade3 = new TradeDetail("T2", 5, "CP-4", "B1", maturityDate, currentDate, 'N');
        tradeTransmissionFlow.addTradeDetail(trade3);
        assertEquals("CP-4", tradeTransmissionFlow.getTradeDetail("T2").getCounterPartyId());
    }

    @Test
    void addTradeWithSameVersionSuccessTest() throws Exception {
        Date maturityDate = sd.parse(sd.format(getFutureDate()));
        TradeDetail trade = new TradeDetail("T1", 1, "CP-1", "B1", maturityDate, currentDate, 'N');
        tradeTransmissionFlow.addTradeDetail(trade);
        //Same Version, Counter-Party ID: CP-2
        TradeDetail tradeModified = new TradeDetail("T1", 1, "CP-2", "B1", maturityDate, currentDate, 'N');
        tradeTransmissionFlow.addTradeDetail(tradeModified);
        assertEquals("CP-2", tradeTransmissionFlow.getTradeDetail("T1").getCounterPartyId());
    }

    @Test
    void addTradeWithLowerVersionFailureTest() throws Exception {
        Date maturityDate = sd.parse(sd.format(getFutureDate()));

        TradeDetail trade = new TradeDetail("T3", 5, "CP-3", "B2", maturityDate, currentDate, 'Y');
        tradeTransmissionFlow.addTradeDetail(trade);
        //Lower version
        TradeDetail tradeModified = new TradeDetail("T3", 1, "CP-3", "B2", maturityDate, currentDate, 'Y');
        assertThrows(Exception.class, () -> tradeTransmissionFlow.addTradeDetail(tradeModified), ":failed due to lesser version");

    }

    @Test
    void addTradeWithHigherMaturitySuccessTest() throws Exception {
        Date maturityDate = sd.parse(sd.format(getFutureDate()));

        TradeDetail trade = new TradeDetail("T3", 5, "CP-3", "B2", maturityDate, currentDate, 'Y');
        tradeTransmissionFlow.addTradeDetail(trade);

        assertEquals(trade, tradeTransmissionFlow.getTradeDetail("T3"));

    }

    @Test
    void addTradeWithLowerMaturityFailureTest() throws Exception {
        Date maturityDate = sd.parse("01/08/2021");
        TradeDetail trade = new TradeDetail("T1", 1, "CP-1", "B1", maturityDate, currentDate, 'N');
        tradeTransmissionFlow.addTradeDetail(trade);
        assertThrows(Exception.class, () -> tradeTransmissionFlow.getTradeDetail("T1"), "1 is less than 5");
    }

    @Test
    void addTradeWithLowerMaturitySameVersionFailureTest() throws Exception {

        Date maturityDate = sd.parse(sd.format(getFutureDate()));
        TradeDetail correctTrade = new TradeDetail("T2", 2, "CP-2", "B1", maturityDate, currentDate, 'N');
        tradeTransmissionFlow.addTradeDetail(correctTrade);

        Date maturityDateLower = sd.parse("01/08/2021");
        TradeDetail incorrectTrade = new TradeDetail("T2", 2, "CP-2", "B1", maturityDateLower, currentDate, 'N');
        tradeTransmissionFlow.addTradeDetail(incorrectTrade);
        assertEquals(maturityDate, tradeTransmissionFlow.getTradeDetail("T2").getMaturityDate());
    }

    @Test
    void addTradeWithCurrentDateAsMaturitySuccessTest() throws Exception {
        TradeDetail trade = new TradeDetail("T3", 3, "CP-3", "B2", currentDate, currentDate, 'N');
        tradeTransmissionFlow.addTradeDetail(trade);
        assertNotNull(tradeTransmissionFlow.getTradeDetail("T3"));
    }

    @Test
    void addTradeWithLowerMaturityHigherVersionFailureTest() throws Exception {

        Date maturityDate = sd.parse(sd.format(getFutureDate()));

        TradeDetail tradeCorrect = new TradeDetail("T2", 2, "CP-2", "B1", maturityDate, currentDate, 'N');
        tradeTransmissionFlow.addTradeDetail(tradeCorrect);
        Date maturityDateLower = sd.parse("01/08/2021");

        TradeDetail tradeInCorrect = new TradeDetail("T2", 3, "CP-3", "B1", maturityDateLower, currentDate, 'N');
        tradeTransmissionFlow.addTradeDetail(tradeInCorrect);
        assertEquals(2, tradeTransmissionFlow.getTradeDetail("T2").getVersion());

    }

    @Test
    void addTradeWithLowerMaturityLowerVersionFailureTest() throws Exception {
        Date maturityDate = sd.parse(sd.format(getFutureDate()));

        TradeDetail correctTrade = new TradeDetail("T1", 2, "CP-1", "B1", maturityDate, currentDate, 'N');
        tradeTransmissionFlow.addTradeDetail(correctTrade);

        maturityDate = sd.parse("01/08/2021");

        TradeDetail incorrectTrade = new TradeDetail("T1", 1, "CP-2", "B1", maturityDate, currentDate, 'N');
        assertThrows(Exception.class, () -> tradeTransmissionFlow.addTradeDetail(incorrectTrade), " :failed due to,lesser version");

    }

    //Check If Maturity Date is Expired it will update the Expired Flag
    @Test
    void updateExpiryFlagForTradeSuccessTest() throws Exception {
        Date maturityDate = sd.parse(sd.format(getFutureDate()));
        TradeDetail trade = new TradeDetail("T3", 3, "CP-4", "B2", maturityDate, currentDate, 'N');
        tradeTransmissionFlow.addTradeDetail(trade);
        tradeTransmissionFlow.getTradeDetail("T3").setMaturityDate(sd.parse("01/08/2021"));
        tradeTransmissionFlow.updateTradeDetailExpiredFlag();
        assertEquals('Y', tradeTransmissionFlow.getTradeDetail("T3").getExpired());
    }

    private Date getFutureDate() {
        Date date = new Date();
        return new Date(date.getTime() + (1000 * 60 * 60 * 24));
    }
}
