package data.econ;

import com.fs.starfarer.api.impl.campaign.econ.impl.BaseIndustry;
import com.fs.starfarer.api.impl.campaign.econ.impl.Farming;
import com.fs.starfarer.api.impl.campaign.ids.Stats;


public class CZIAutofabHub {

    public static float EXPORT_REDUCTION = -5f;
    public static float S_DEMAND_REDUCTION = 1f;
    public static float L_DEMAND_REDUCTION = 2f;
    public static float Size_large = 6f;
    public static float Size_Small = 5f;


   /* //@Override
    public void apply() {
        super.apply(true);
        int size = market.getSize();

        //market.getExportIncome().modifyPercent(getModId(0), EXPORT_REDUCTION, getNameForModifier()); //reduces export value for industry
        demandReduction.setBaseValue(1);

        //market.getMaxindustries

        if (!isFunctional()) {
            unapply();
        }
    }


    @Override
    public void unapply() {
        super.unapply();


    }*/
}
