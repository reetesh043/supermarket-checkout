package com.reet.domain;

import java.util.Objects;
import java.util.StringJoiner;
/*
*  Rule object
*/
public class Rule {

    private String ruleName;
    private String itemId;
    private String finalPrice;
    private String ruleCondition;

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public void setFinalPrice(String finalPrice) {
        this.finalPrice = finalPrice;
    }

    public void setRuleCondition(String ruleCondition) {
        this.ruleCondition = ruleCondition;
    }

    public String getRuleCondition() {
        return ruleCondition;
    }

    public String getFinalPrice() {
        return finalPrice;
    }

    public String getItemId() {
        return itemId;
    }

    public String getRuleName() {
        return ruleName;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rule rule = (Rule) o;
        return Objects.equals(itemId, rule.itemId) && Objects.equals(ruleName, rule.ruleName) && Objects.equals(finalPrice, rule.finalPrice) && Objects.equals(ruleCondition, rule.ruleCondition);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemId, ruleName, finalPrice, ruleCondition);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Rule.class.getSimpleName() + "[", "]")
                .add("itemId='" + itemId + "'")
                .add("ruleName='" + ruleName + "'")
                .add("finalPrice='" + finalPrice + "'")
                .add("ruleCondition='" + ruleCondition + "'")
                .toString();
    }
}
