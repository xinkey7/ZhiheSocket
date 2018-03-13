package com.example.tecpie.ZhiheSocket.pojo;

/**
 * 接受收益数据请求结果
 */
public class RespondProfitInfoBean {
    private boolean result;
    private double profit;
    private double profit_total;

    public boolean getResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public double getProfit() {
        return profit;
    }

    public void setProfit(double profit) {
        this.profit = profit;
    }

    public double getProfit_total() {
        return profit_total;
    }

    public void setProfit_total(double profit_total) {
        this.profit_total = profit_total;
    }
}
