import FirefighterCheatsheetClient from '../api/firefighterCheatsheetClient';

/**
 * The constantsPopulator component for the website.
 */
export default class ConstantsPopulator {
    constructor() {

        this.client = new FirefighterCheatsheetClient();
    }

    async populateAll() {
        var fullConstantsList = await this.client.getConstants();

        var colorList = [];
        var lengthList = [];
        var diameterList = [];
        var gpmList = [];

        for (var i=0; i < fullConstantsList.length; i++) {
            var currentConstant = fullConstantsList[i];

            if (currentConstant.key == "color") {
                colorList.push(currentConstant);
            }

            if (currentConstant.key == "length") {
                lengthList.push(currentConstant);
            }

            if (currentConstant.key == "diameter") {
                diameterList.push(currentConstant);
            }

            if (currentConstant.key == "gpm") {
                gpmList.push(currentConstant);
            }
        }

        this.populateColors(colorList);
        this.populateLengths(lengthList);
        this.populateDiameters(diameterList);
        this.populateGPM(gpmList);
    }

    async populateColors(colorList) {

        colorList.sort(this.sortTool);

        for (var i=0; i < colorList.length; i++) {
            var humanValue = colorList[i].humanValue;
            var computerValue = colorList[i].computerValue;
            document.getElementById('colorSelector').innerHTML += 
           `<option value='${computerValue}'>${humanValue}</option>`;
        }

    }

    async populateLengths(lengthList) {
        lengthList.sort(this.sortTool);

        for (var i=0; i < lengthList.length; i++) {
            var humanValue = lengthList[i].humanValue;
            var computerValue = lengthList[i].computerValue;
            document.getElementById('lengthSelector').innerHTML += 
           `<option value='${computerValue}'>${humanValue}</option>`;
        }
        
    }

    async populateDiameters(diameterList) {
        diameterList.sort(this.sortTool);

        for (var i=0; i < diameterList.length; i++) {
            var humanValue = diameterList[i].humanValue;
            var computerValue = diameterList[i].computerValue;
            document.getElementById('diameterSelector').innerHTML += 
           `<option value='${computerValue}'>${humanValue}</option>`;
        }

    }

    async populateGPM(gpmList) {
        gpmList.sort(this.sortTool);

        for (var i=0; i < gpmList.length; i++) {
            var humanValue = gpmList[i].humanValue;
            var computerValue = gpmList[i].computerValue;
            document.getElementById('gpmSelector').innerHTML += 
           `<option value='${computerValue}'>${humanValue}</option>`;
        }

    }

    sortTool(a, b) {
        if (a.humanValue < b.humanValue) {
            return -1
        }
        if (a.humanValue > b.humanValue) {
            return 1;
        }
        return 0;
    }
    
}