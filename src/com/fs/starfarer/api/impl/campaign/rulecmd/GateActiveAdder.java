package com.fs.starfarer.api.impl.campaign.rulecmd;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.*;
import com.fs.starfarer.api.campaign.rules.MemoryAPI;
import com.fs.starfarer.api.impl.campaign.GateEntityPlugin;
import com.fs.starfarer.api.impl.campaign.rulecmd.BaseCommandPlugin;
import com.fs.starfarer.api.impl.campaign.rulecmd.missions.GateCMD;
import com.fs.starfarer.api.util.Misc;


import java.util.List;
import java.util.Map;


public class GateActiveAdder extends BaseCommandPlugin{

    protected CampaignFleetAPI playerFleet;
    protected SectorEntityToken entity;
    protected TextPanelAPI text;
    protected OptionPanelAPI options;
    protected CargoAPI playerCargo;
    protected MemoryAPI memory;
    protected InteractionDialogAPI dialog;
    protected Map<String, MemoryAPI> memoryMap;

    //protected SectorEntityToken "ui_ellen_trechend_gate";

    //protected SectorEntityToken "alkanost_gate";


    public boolean execute(String ruleId, InteractionDialogAPI dialog, List<Misc.Token> params, Map<String, MemoryAPI> memoryMap) {

        this.dialog = dialog;
        this.memoryMap = memoryMap;

        String command = params.get(0).getString(memoryMap);
        if (command == null) return false;

        memory = getEntityMemory(memoryMap);

        entity = dialog.getInteractionTarget();
        text = dialog.getTextPanel();
        options = dialog.getOptionPanel();

        playerFleet = Global.getSector().getPlayerFleet();
        playerCargo = playerFleet.getCargo();

        if (command.equals("notifyScanned")) {
            SectorEntityToken otherGate1 = Global.getSector().getEntityById("alkanost_gate");
            SectorEntityToken otherGate2 = Global.getSector().getEntityById("ui_ellen_trechend_gate");
            GateCMD.notifyScanned(otherGate1);
            GateCMD.notifyScanned(otherGate2);
        }
        return true;
    }
}
