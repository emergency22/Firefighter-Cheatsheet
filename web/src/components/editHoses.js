import FirefighterCheatsheetClient from '../api/firefighterCheatsheetClient';
import ConstantsPopulator from '../components/constantsPopulator';

/**
 * The editHoses component for the website.
 */
export default class EditHoses {
    constructor() {

        this.client = new FirefighterCheatsheetClient();
        this.constantsPopulator = new ConstantsPopulator();
    }

    /**
     * Display hoses on the page.
     */
    async displayHoses(fireDept, apparatusTypeAndNumber) {

        const apparatus = await this.client.getIndividualApparatus(fireDept, apparatusTypeAndNumber);
        document.getElementById('theDisplayArea').innerHTML = "";
        document.getElementById('addApparatusForm').innerHTML = "";
        document.getElementById('addHoseFormMain').innerHTML = "";

        if (apparatus.hoseList.length == 0) {
            document.getElementById('theDisplayArea').innerHTML = "No hoses exist for this apparatus. Add a hose below."
        }

        document.getElementById('theDisplayArea').innerHTML += "<div class='individualApparatus' id='individualApparatus'>" + fireDept + " " + apparatusTypeAndNumber + "</div>";

        const hoseList = apparatus.hoseList;

        var currentHoseLocation = "currentHoseLocation";
        var currentHoseLocations = [];

        for (var i=0; i < hoseList.length; i++) {

            let iString = i.toString();
            currentHoseLocation += iString;

            var currentHose = hoseList[i];
            if (currentHose != null) {
                var name = currentHose.name;
                var color = currentHose.color;
                var length = currentHose.length;
                var hoseDiameter = currentHose.hoseDiameter;
                var waterQuantityInGallons = currentHose.waterQuantityInGallons;
                var pumpDischargePressure = currentHose.pumpDischargePressure;

                var apparatusHoseInfo = 
                "<li><u>" + color + 
                " " + 
                name + 
                "</u> - " + 
                length +
                " Feet, " + 
                hoseDiameter +
                '" Diameter, ' + 
                waterQuantityInGallons +
                " GPM " +
                "<span>" +
                    `<div class='delButton' id='${currentHoseLocation}'>X</div>` +
                `</span><div class='editHoses'>` +

                `${pumpDischargePressure} PSI on Fog Nozzle` + "</div></li>";


                document.getElementById('theDisplayArea').innerHTML += apparatusHoseInfo;
                
                currentHoseLocations.push(currentHoseLocation);

                currentHoseLocation = "currentLocation";   //reset variable for the next loop
            }
        }
        for (var i=0; i < hoseList.length; i++) {
            var thisCurrentHoseLocation = currentHoseLocations[i];
            var hoseIndexNumber = i;

            this.createDeleteHoseButton(thisCurrentHoseLocation, fireDept, apparatusTypeAndNumber, hoseIndexNumber);
    }
    this.displayAddHoseMenu(fireDept, apparatusTypeAndNumber);
}

    createDeleteHoseButton(thisCurrentHoseLocation, fireDept, apparatusTypeAndNumber, hoseIndexNumber) {
        const button = document.getElementById(thisCurrentHoseLocation);
        button.classList.add('button');
        button.classList.add(thisCurrentHoseLocation);

        button.addEventListener('click', async () => {
            if (confirm("Click OK to delete this hose.") == true) {
            await this.client.deleteHose(fireDept, apparatusTypeAndNumber, hoseIndexNumber);
            await this.displayHoses(fireDept, apparatusTypeAndNumber);  //reload the page
            }
        });
        return button;
    }

    displayAddHoseMenu(fireDept, apparatusTypeAndNumber) {
        (document.getElementById('addHoseFormMain').innerHTML += "<form class='addHoseForm' id='addHoseForm'>" +
            "<label for='fireDept'>Add a hose: </label>" +
            " &nbsp &nbsp <input type='text' id='name' placeHolder='Name of Hose' style='width: 200px' required>" +

            `  &nbsp &nbsp      
            <select id='colorSelector' name='colorSelector' required>
            <option value="">Color</option>
            </select>
           ` +

            `  &nbsp &nbsp      
            <select id='lengthSelector' name='lengthSelector' required>
            <option value="">Hose Length</option>
            </select>
            ` +

            ` &nbsp &nbsp
            <select id='diameterSelector' name='diameterSelector' required>
            <option value="">Hose Diameter</option>
            </select>
            ` +

            `  &nbsp &nbsp     
            <select id='gpmSelector' name='gpmSelector' required>
            <option value="">GPM</option>
            </select>
            ` +

            " &nbsp  &nbsp <input type='submit' style='font-weight:bold' value='Add Hose'></form>"
        );
            this.addHoseFormSubmitter(fireDept, apparatusTypeAndNumber);
            this.constantsPopulator.populateAll();
    }

    addHoseFormSubmitter(fireDept, apparatusTypeAndNumber) {
        var addHoseFormToClient = document.getElementById('addHoseForm');
        addHoseFormToClient.addEventListener('submit', async (event) => {
            event.preventDefault()  //prevents auto-submit

            var inputName = document.getElementById('name').value;
            var inputColor = document.getElementById('colorSelector').value;
            var inputLength = document.getElementById('lengthSelector').value;
            var inputDiameter = document.getElementById('diameterSelector').value;
            var inputGallons = document.getElementById('gpmSelector').value;
    
            await this.client.addHose(fireDept, apparatusTypeAndNumber, inputName, inputColor, inputLength, inputDiameter, inputGallons);
            await this.displayHoses(fireDept, apparatusTypeAndNumber);
        });
    }

}