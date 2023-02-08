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
        console.log("apparatus: " + apparatus);

        if (apparatus == null) {
            document.getElementById('theDisplayArea').innerHTML = "No hoses exist for this apparatus. Add a hose below."
        }

        document.getElementById('theDisplayArea').innerHTML += "<div class='individualApparatus' id='individualApparatus'>" + fireDept + " " + apparatusTypeAndNumber + "</div>";

        const hoseList = apparatus.hoseList;
        console.log(hoseList.length);

        var currentHoseLocation = "currentHoseLocation";
        var currentHoseLocations = [];
        var currentHoses = [];


        for (var i=0; i < hoseList.length; i++) {
            console.log(i);
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


                var apparatusInfo = 
                "<li>" + color + 
                " " + 
                name + 
                " - " + 
                length +
                " Feet, " + 
                hoseDiameter +
                " Diameter, " + 
                waterQuantityInGallons +
                " Gallons, " + 
                pumpDischargePressure +
                " PSI " +
                "<span>" +
                    `<div class='delButton' id='${currentHoseLocation}'>X</div>` +
                `</span><div class='editHoses' id='${currentHose}'>` +
                `Calculate pump discharge pressure for ${color} ${name}` + "</div></li>";
                document.getElementById('theDisplayArea').innerHTML += apparatusInfo;
                currentHoseLocations.push(currentHoseLocation);
                currentHoses.push(currentHose);

                currentHoseLocation = "currentLocation";   //reset variable for the next loop
                currentHose = "currentHose";  //reset variable for the next loop;
            }


        }
    }
}