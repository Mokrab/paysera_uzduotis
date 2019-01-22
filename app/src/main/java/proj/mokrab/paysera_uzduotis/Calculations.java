package proj.mokrab.paysera_uzduotis;

import org.json.JSONException;
import org.json.JSONObject;
import java.text.NumberFormat;
import java.util.Currency;

public class Calculations {

    private Double eurBalance;
    private Double usdBalance;
    private Double jpyBalance;
    private Double inputAmount;
    private Double commissionFee;
    private Double freeCommissionFee;
    private String jsonData;
    private String from;
    private String to;
    private String result;
    private Double eurBalanceResult;
    private Double usdBalanceResult;
    private Double storedCommissions;
    private Double jpyBalanceResult;
    private Integer timesConverted;


    public Calculations() {
    }

    public String calculateBalance() {
        result = "";
        freeCommissionFee = 0.00;
        NumberFormat format = NumberFormat.getInstance();
        format.setCurrency(Currency.getInstance(from));
        switch (from) {
            /////////////////////////////////////////////////////////////////////////////////////////////////////EUR//////////////////////////////////////////////////////////////////////////////////////////////////////////////
            case "EUR":
                if (timesConverted < 5) {
                    if ((eurBalance - inputAmount) >= 0) {
                        if (to.equals("USD")) {
                            timesConverted++;
                            eurBalanceResult = eurBalance - inputAmount;
                            usdBalanceResult = usdBalance + Double.valueOf(getJsonDataAmount());
                            jpyBalanceResult = jpyBalance;
                            result = "You have converted " + inputAmount.toString() + from + " to " + getJsonDataAmount() + to + ". Commission fee: " + format.format(freeCommissionFee) + " " + from + " (Free)";
                            break;
                        } else if (to.equals("JPY")) {
                            timesConverted++;
                            eurBalanceResult = eurBalance - inputAmount;
                            usdBalanceResult = usdBalance;
                            jpyBalanceResult = jpyBalance + Double.valueOf(getJsonDataAmount());
                            result = "You have converted " + inputAmount.toString() + from + " to " + getJsonDataAmount() + to + ". Commission fee: " + format.format(freeCommissionFee) + " " + from + " (Free)";
                            break;
                        } else {
                            eurBalanceResult = eurBalance;
                            usdBalanceResult = usdBalance;
                            jpyBalanceResult = jpyBalance;
                            result = "Cannot convert " + from + " to " + from;
                            break;
                        }
                    } else {
                        eurBalanceResult = eurBalance;
                        usdBalanceResult = usdBalance;
                        jpyBalanceResult = jpyBalance;
                        result = "Not enough " + from + " in balance.";
                        break;
                    }

                } else {
                    setCommissionFee(0.7);
                    Double f = inputAmount * (commissionFee / 100);
                    if (eurBalance - inputAmount - (inputAmount * (commissionFee / 100)) >= 0) {
                        if (to.equals("USD")) {
                            timesConverted++;
                            eurBalanceResult = eurBalance - inputAmount - f;
                            usdBalanceResult = usdBalance + Double.valueOf(getJsonDataAmount());
                            jpyBalanceResult = jpyBalance;
                            storedCommissions = storedCommissions + inputAmount * (commissionFee / 100);
                            result = "You have converted " + inputAmount.toString() + from + " to " + getJsonDataAmount() + to + ". Commission: " + format.format(f) + " " + from + " (" + format.format(commissionFee) + "%)";
                            break;
                        } else if (to.equals("JPY")) {
                            timesConverted++;
                            eurBalanceResult = eurBalance - inputAmount - f;
                            usdBalanceResult = usdBalance;
                            jpyBalanceResult = jpyBalance + Double.valueOf(getJsonDataAmount());
                            storedCommissions = storedCommissions + inputAmount * (commissionFee / 100);
                            result = "You have converted " + inputAmount.toString() + from + " to " + getJsonDataAmount() + to + ". Commission: " + format.format(f) + " " + from + " (" + format.format(commissionFee) + "%)";
                            break;
                        } else {
                            eurBalanceResult = eurBalance;
                            usdBalanceResult = usdBalance;
                            jpyBalanceResult = jpyBalance;
                            result = "Cannot convert " + from + " to " + from;
                            break;
                        }
                    } else {
                        result = "Not enough " + from + " in balance.";
                        break;
                    }
                }
                /////////////////////////////////////////////////////////////////////////////////////////////////////USD//////////////////////////////////////////////////////////////////////////////////////////////////////////////
            case "USD":
                if (timesConverted < 5) {
                    if ((usdBalance - inputAmount) >= 0) {
                        if (to.equals("EUR")) {
                            timesConverted++;
                            eurBalanceResult = eurBalance + Double.valueOf(getJsonDataAmount());
                            usdBalanceResult = usdBalance - inputAmount;
                            jpyBalanceResult = jpyBalance;
                            result = "You have converted " + inputAmount.toString() + from + " to " + getJsonDataAmount() + to + ". Commission fee: " + format.format(freeCommissionFee) + " " + from + " (Free)";
                            break;
                        } else if (to.equals("JPY")) {
                            timesConverted++;
                            eurBalanceResult = eurBalance;
                            usdBalanceResult = usdBalance - inputAmount;
                            jpyBalanceResult = jpyBalance + Double.valueOf(getJsonDataAmount());
                            result = "You have converted " + inputAmount.toString() + from + " to " + getJsonDataAmount() + to + ". Commission fee: " + format.format(freeCommissionFee) + " " + from + " (Free)";
                            break;
                        } else {
                            eurBalanceResult = eurBalance;
                            usdBalanceResult = usdBalance;
                            jpyBalanceResult = jpyBalance;
                            result = "Cannot convert " + from + " to " + from;
                            break;
                        }
                    } else {
                        eurBalanceResult = eurBalance;
                        usdBalanceResult = usdBalance;
                        jpyBalanceResult = jpyBalance;
                        result = "Not enough " + from + " in balance.";
                        break;
                    }

                } else {
                    setCommissionFee(0.7);
                    Double f = inputAmount * (commissionFee / 100);

                    if (usdBalance - inputAmount - (inputAmount * (commissionFee / 100)) >= 0) {
                        if (to.equals("EUR")) {
                            timesConverted++;
                            eurBalanceResult = eurBalance + Double.valueOf(getJsonDataAmount());
                            usdBalanceResult = usdBalance - inputAmount - f;
                            jpyBalanceResult = jpyBalance;
                            storedCommissions = storedCommissions + inputAmount * (commissionFee / 100);
                            result = "You have converted " + inputAmount.toString() + from + " to " + getJsonDataAmount() + to + ". Commission: " + format.format(f) + " " + from + " (" + format.format(commissionFee) + "%)";
                            break;

                        } else if (to.equals("JPY")) {
                            timesConverted++;
                            eurBalanceResult = eurBalance;
                            usdBalanceResult = usdBalance - inputAmount - f;
                            jpyBalanceResult = jpyBalance + Double.valueOf(getJsonDataAmount());
                            storedCommissions = storedCommissions + inputAmount * (commissionFee / 100);
                            result = "You have converted " + inputAmount.toString() + from + " to " + getJsonDataAmount() + to + ". Commission: " + format.format(f) + " " + from + " (" + format.format(commissionFee) + "%)";
                            break;
                        } else {
                            eurBalanceResult = eurBalance;
                            usdBalanceResult = usdBalance;
                            jpyBalanceResult = jpyBalance;
                            result = "Cannot convert " + from + " to " + from;
                            break;
                        }

                    } else {
                        result = "Not enough " + from + " in balance.";
                        break;
                    }
                }


                /////////////////////////////////////////////////////////////////////////////////////////////////////JPY//////////////////////////////////////////////////////////////////////////////////////////////////////////////
            case "JPY":

                if (timesConverted < 5) {
                    if ((jpyBalance - inputAmount.intValue()) >= 0) {

                        if (to.equals("EUR")) {
                            timesConverted++;
                            eurBalanceResult = eurBalance + Double.valueOf(getJsonDataAmount());
                            usdBalanceResult = usdBalance;
                            jpyBalanceResult = jpyBalance - inputAmount;
                            result = "You have converted " + inputAmount.toString() + from + " to " + getJsonDataAmount() + to + ". Commission fee: " + format.format(freeCommissionFee) + " " + from + " (Free)";
                            break;

                        } else if (to.equals("USD")) {
                            timesConverted++;
                            eurBalanceResult = eurBalance;
                            usdBalanceResult = usdBalance + Double.valueOf(getJsonDataAmount());
                            jpyBalanceResult = jpyBalance - inputAmount;
                            result = "You have converted " + inputAmount.toString() + from + " to " + getJsonDataAmount() + to + ". Commission fee: " + format.format(freeCommissionFee) + " " + from + " (Free)";
                            break;
                        } else {
                            eurBalanceResult = eurBalance;
                            usdBalanceResult = usdBalance;
                            jpyBalanceResult = jpyBalance;
                            result = "Cannot convert " + from + " to " + from;
                            break;
                        }
                    } else {
                        eurBalanceResult = eurBalance;
                        usdBalanceResult = usdBalance;
                        jpyBalanceResult = jpyBalance;
                        result = "Not enough " + from + " in balance.";
                        break;
                    }
                } else {
                    setCommissionFee(0.7);
                    Double f = inputAmount * (commissionFee / 100);
                    if (jpyBalance - inputAmount.intValue() - f.intValue() >= 0) {
                        if (to.equals("EUR")) {
                            timesConverted++;
                            eurBalanceResult = eurBalance + Double.valueOf(getJsonDataAmount());
                            usdBalanceResult = usdBalance;
                            jpyBalanceResult = jpyBalance - inputAmount - f;
                            storedCommissions = storedCommissions + inputAmount * (commissionFee / 100);
                            result = "You have converted " + inputAmount.toString() + from + " to " + getJsonDataAmount() + to + ". Commission: " + format.format(f) + " " + from + " (" + format.format(commissionFee) + "%)";
                            break;

                        } else if (to.equals("USD")) {
                            timesConverted++;
                            eurBalanceResult = eurBalance;
                            usdBalanceResult = usdBalance + Double.valueOf(getJsonDataAmount());
                            jpyBalanceResult = jpyBalance - inputAmount- f;
                            storedCommissions = storedCommissions + inputAmount * (commissionFee / 100);
                            result = "You have converted " + inputAmount.toString() + from + " to " + getJsonDataAmount() + to + ". Commission: " + format.format(f) + " " + from + " (" + format.format(commissionFee) + "%)";
                            break;
                        } else {
                            eurBalanceResult = eurBalance;
                            usdBalanceResult = usdBalance;
                            jpyBalanceResult = jpyBalance;
                            result = "Cannot convert " + from + " to " + from;
                            break;
                        }
                    } else {
                        eurBalanceResult = eurBalance;
                        usdBalanceResult = usdBalance;
                        jpyBalanceResult = jpyBalance;
                        result = "Not enough " + from + " in balance.";
                        break;
                    }

                }
        }

        return result;
    }


    public void setEurBalance(Double eur) {

        this.eurBalance = eur;

    }

    public void setUsdBalance(Double usd) {

        this.usdBalance = usd;

    }

    public void setJpyBalance(Double jpy) {

        this.jpyBalance = jpy;

    }

    public void setAmount(Double amount) {
        this.inputAmount = amount;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public void setJsonData(String jsonData) {
        this.jsonData = jsonData;
    }

    public void setCommissionFee(Double fee) {
        this.commissionFee = fee;
    }

    public void setTimesConverted(Integer times) {
        this.timesConverted = times;
    }

    public void setStoredCommissions(Double amount) {
        this.storedCommissions = amount;
    }


    public Double getEurBalanceResult() {
        return eurBalanceResult;
    }

    public Double getUsdBalanceResult() {
        return usdBalanceResult;
    }

    public Double getJpyBalanceResult() {
        return jpyBalanceResult;
    }

    public Integer getTimesConverted() {
        return timesConverted;
    }

    private String getJsonDataAmount() {
        String getJsonAmount = "";
        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            getJsonAmount = String.valueOf(jsonObject.get("amount"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return getJsonAmount;
    }

    public Double getStoredCommissions() {

        return storedCommissions;
    }

}
