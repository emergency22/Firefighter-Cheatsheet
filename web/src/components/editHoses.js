import FirefighterCheatsheetClient from '../api/firefighterCheatsheetClient';

/**
 * The header component for the website.
 */
export default class EditHoses {
    constructor() {
        // super();

        // const methodsToBind = [
        //     'addHeaderToPage', 'createSiteTitle', 'createUserInfoForHeader',
        //     'createLoginButton', 'createLoginButton', 'createLogoutButton', 'createUserInterface'
        // ];
        // this.bindClassMethods(methodsToBind, this);

        this.client = new FirefighterCheatsheetClient();
        // this.editHoses = new EditHoses();
    }

    /**
     * Display hoses on the page.
     */
    async displayHoses(fireDept, apparatusTypeAndNumber) {

        const apparatus = await this.client.getIndividualApparatus(fireDept, apparatusTypeAndNumber);
        document.getElementById('theDisplayArea').innerHTML = "";
        document.getElementById('addApparatusForm').innerHTML = "";
        document.getElementById('addHoseFormMain').innerHTML = "";
        console.log("apparatus: " + apparatus);

        if (apparatus.hoseList.length == 0) {
            document.getElementById('theDisplayArea').innerHTML = "No hoses exist for this apparatus. Add a hose below."
        }

        document.getElementById('theDisplayArea').innerHTML += "<div class='individualApparatus' id='individualApparatus'>" + fireDept + " " + apparatusTypeAndNumber + "</div>";

        const hoseList = apparatus.hoseList;
        console.log(hoseList.length);

        var currentHoseLocation = "currentHoseLocation";
        var currentHoseLocations = [];
        // var currentHoses = [];


        for (var i=0; i < hoseList.length; i++) {

            let iString = i.toString();
            currentHoseLocation += iString;
            // currentHose += iString;

            var currentHose = hoseList[i];
            if (currentHose != null) {
                var name = currentHose.name;
                var color = currentHose.color;
                var length = currentHose.length;
                var hoseDiameter = currentHose.hoseDiameter;
                var waterQuantityInGallons = currentHose.waterQuantityInGallons;
                var pumpDischargePressure = currentHose.pumpDischargePressure;
                console.log(name);
                console.log(color);
                console.log(length);
                console.log(hoseDiameter);
                console.log(waterQuantityInGallons);
                console.log(pumpDischargePressure);


                var apparatusHoseInfo = 
                "<li>" + color + 
                " " + 
                name + 
                " - " + 
                length +
                " Feet, " + 
                hoseDiameter +
                '" Diameter, ' + 
                waterQuantityInGallons +
                " GPM, " + 
                pumpDischargePressure +
                " PSI " +
                "<span>" +
                    `<div class='delButton' id='${currentHoseLocation}'>X</div>` +
                `</span><div class='editHoses' id='${currentHose}'>` +
                `Calculate pump discharge pressure for ${color} ${name}` + "</div></li>";
                document.getElementById('theDisplayArea').innerHTML += apparatusHoseInfo;
                currentHoseLocations.push(currentHoseLocation);
                // currentHoses.push(currentHose);

                currentHoseLocation = "currentLocation";   //reset variable for the next loop
                // currentHose = "currentHose";  //reset variable for the next loop;
            }
        }
        for (var i=0; i < hoseList.length; i++) {
            var currentHoseLocation = currentHoseLocations[i];
        //     var currentHose = currentHoses[i];
        //     var fireDept = hoseList[i].fireDept;
            var hoseIndexNumber = i;

            this.createDeleteHoseButton(currentHoseLocation, fireDept, apparatusTypeAndNumber, hoseIndexNumber);
        //     this.createCalculatePDPButton(currentHose, fireDept, apparatusTypeAndNumber);
        // }
    }
    this.displayAddHoseMenu(fireDept, apparatusTypeAndNumber);
}

    createDeleteHoseButton(currentHoseLocation, fireDept, apparatusTypeAndNumber, hoseIndexNumber) {
        const button = document.getElementById(currentHoseLocation);
        button.classList.add('button');
        button.classList.add(currentHoseLocation);

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
            "<input type='text' id='name' placeHolder='Name of Hose' style='width: 200px' required>" +

            // "<input type='text' id='color' placeHolder='Color' style='width: 200px' required>" +

            `       
            <select id='colorSelector' name='colorSelector' required>
                <option value="">Color</option>
                <option value="white">White</option>
                <option value="green">Green</option>
                <option value="red">Red</option>
                <option value="yellow">Yellow</option>
                <option value="orange">Orange</option>
                <option value="blue">Blue</option>
                <option value="purple">Purple</option>
                <option value="brown">Brown</option>
                <option value="black">Black</option>
            </select>
           ` +

            // "<input type='text' id='length' placeHolder='Length in Feet' style='width: 200px' required>" +

            `       
            <select id='lengthSelector' name='lengthSelector' required>
                <option value="">Length in Feet</option>
                <option value="50">50 Feet</option>
                <option value="100">100 Feet</option>
                <option value="150">150 Feet</option>
                <option value="200">200 Feet</option>
                <option value="250">250 Feet</option>
                <option value="300">300 Feet</option>
                <option value="350">350 Feet</option>
                <option value="400">400 Feet</option>
                <option value="450">450 Feet</option>
                <option value="500">500 Feet</option>
            </select>
            ` +

            // "<input type='text' id='gallons' placeHolder='Total Gallonage' style='width: 200px' required>" +

            `       
            <select id='gpmSelector' name='gpmSelector' required>
                <option value="">Gallons Per Minute</option>
                <option value="50">50 GPM</option>
                <option value="100">100 GPM</option>
                <option value="150">150 GPM</option>
                <option value="200">200 GPM</option>
                <option value="250">250 GPM</option>
                <option value="300">300 GPM</option>
                <option value="350">350 GPM</option>
                <option value="400">400 GPM</option>
                <option value="450">450 GPM</option>
                <option value="500">500 GPM</option>
            </select>
            ` +

            "<input type='submit' style='font-weight:bold' value='Add Hose'></form>"
        );
            this.addHoseFormSubmitter(fireDept, apparatusTypeAndNumber);
    }

    addHoseFormSubmitter(fireDept, apparatusTypeAndNumber) {
        var addHoseFormToClient = document.getElementById('addHoseForm');
        addHoseFormToClient.addEventListener('submit', async (event) => {
            event.preventDefault()  //prevents auto-submit

            var inputName = document.getElementById('name').value;
            var inputColor = document.getElementById('colorSelector').value;
            var inputLength = document.getElementById('lengthSelector').value;
            var inputGallons = document.getElementById('gpmSelector').value;
    
            console.log("yup");
            await this.client.addHose(fireDept, apparatusTypeAndNumber, inputName, inputColor, inputLength, inputGallons);
            console.log("uh huh");

            await this.displayHoses();
        });
    }


}