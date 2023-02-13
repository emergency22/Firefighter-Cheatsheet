import FirefighterCheatsheetClient from '../api/firefighterCheatsheetClient';

/**
 * The editHoses component for the website.
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
    }

    async displayCheatsheet() {
        document.getElementById('theDisplayArea').innerHTML = "";
        document.getElementById('addApparatusForm').innerHTML = "";

        const apparatusList = await this.client.getApparatus(); 

        if (apparatusList.length == 0) {
            document.getElementById('theDisplayArea').innerHTML = "No apparatus exist for this account. Go back to add Apparatus.";
        }

        //for each apparatus, display the apparatus fireDept and type/num first. Then display each hose.
        //for each hose, display the Color, Name, and PSI in that order.

        for (var i=0; i < apparatusList.length; i++) {
            
            // let iString = i.toString();
            // currentLocation += iString;
            // currentHose += iString;

            var currentApparatus = apparatusList[i];
            if (currentApparatus.fireDept != null) {
                var fireDept = currentApparatus.fireDept;
                var apparatusTypeAndNumber = currentApparatus.apparatusTypeAndNumber;
                var hoseList = currentApparatus.hoseList;

                document.getElementById('theDisplayArea').innerHTML += "<div class='individualApparatus' id='individualApparatus'>" + fireDept + " " + apparatusTypeAndNumber + "<div id='thisHoseList'></div>";

                for (var i=0; i < hoseList.length; i++) {
                    
                    var currentHose = hoseList[i];
                    if (currentHose != null) {
                    var name = currentHose.name;
                    var color = currentHose.color;
                    var pumpDischargePressure = currentHose.pumpDischargePressure;

                    var apparatusHoseInfo = 
                    "<li>" + color +
                    " " +
                    name +
                    " - " +
                    pumpDischargePressure +
                    " PSI ";
                    document.getElementById('thisHoseList').innerHTML += apparatusHoseInfo;
                    }

                }


                // var apparatusInfo = 
                // "<li>" + fireDept + 
                // " " + 
                // apparatusTypeAndNumber + 
                // "<span>" +
                //     `<div class='delButton' id='${currentLocation}'>X</div>` +
                // `</span><div class='editHoses' id='${currentHose}'>` +
                // `Edit Hoses for ${fireDept} ${apparatusTypeAndNumber}` + "</div></li>";
                // document.getElementById('theDisplayArea').innerHTML += apparatusInfo;
                // currentLocations.push(currentLocation);
                // currentHoses.push(currentHose);

                // currentLocation = "currentLocation";   //reset variable for the next loop
                // currentHose = "currentHose";  //reset variable for the next loop;
            }
        }

    }
}