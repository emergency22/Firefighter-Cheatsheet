import { establishRelationAndKeys } from '@aws-amplify/datastore/lib-esm/util';
import { formToJSON } from 'axios';
import FirefighterCheatsheetClient from '../api/firefighterCheatsheetClient';
import BindingClass from "../util/bindingClass";
import EditHoses from "../components/editHoses";

/**
 * The header component for the website.
 */
export default class Header extends BindingClass {
    constructor() {
        super();

        const methodsToBind = [
            'addHeaderToPage', 'createSiteTitle', 'createUserInfoForHeader',
            'createLoginButton', 'createLoginButton', 'createLogoutButton', 'createUserInterface'
        ];
        this.bindClassMethods(methodsToBind, this);

        this.client = new FirefighterCheatsheetClient();
        this.editHoses = new EditHoses();
    }

    /**
     * Add the header to the page.
     */
    async addHeaderToPage() {
        const currentUser = await this.client.getIdentity();

        const siteTitle = this.createSiteTitle();
        const userInfo = this.createUserInfoForHeader(currentUser);

        const header = document.getElementById('loginArea');
        header.appendChild(siteTitle);
        header.appendChild(userInfo);
    }

    createSiteTitle() {
        const homeButton = document.createElement('a');
        homeButton.classList.add('header_home');
        homeButton.href = 'index.html';
        homeButton.innerText = null;   //Orignally 'Playlists'

        const siteTitle = document.createElement('div');
        siteTitle.classList.add('site-title');
        siteTitle.appendChild(homeButton);

        return siteTitle;
    }

    createUserInfoForHeader(currentUser) {
        const userInfo = document.createElement('div');
        userInfo.classList.add('user');

        const childContent = currentUser
            ? this.createLogoutButton(currentUser)
            : this.createLoginButton();

        userInfo.appendChild(childContent);

        return userInfo;
    }

    createLoginButton() {
        return this.createButton('Login', this.client.login);
    }

    createLogoutButton(currentUser) {
        this.createUserInterface();
        return this.createButton(`Logout: ${currentUser.name}`, this.client.logout);
    }

    createButton(text, clickHandler) {
        const button = document.createElement('a');
        button.classList.add('button');
        button.href = '#';
        button.innerText = text;

        button.addEventListener('click', async () => {
            await clickHandler();
        });

        return button;
    }

    createUserInterface() {
        const interfaceArea = document.getElementById('userInterfaceArea');
        interfaceArea.classList.remove('hidden');
        // const hamToggler = document.getElementById('hamToggle');
        // hamToggler.classList.remove('hidden');
        const cheatsheetButtonToggler = document.getElementById('cheatSheetButtonToggle')
        cheatsheetButtonToggler.classList.remove('hidden');
        this.displayApparatus();
        this.displayAddApparatusMenu();
    }

    async displayApparatus() {
        document.getElementById('theDisplayArea').innerHTML = "";
        const apparatusList = await this.client.getApparatus();     //may want to set apparatusList in the datastore later. dunno.
        if (apparatusList.length == 0) {
            document.getElementById('theDisplayArea').innerHTML = "No apparatus exist for this account. Add an apparatus below."
        }

        var currentLocation = "currentLocation";
        var currentHose = "currentHose";
        var currentLocations = [];
        var currentHoses = [];

        for (var i=0; i < apparatusList.length; i++) {
            
            let iString = i.toString();
            currentLocation += iString;
            currentHose += iString;

            var currentApparatus = apparatusList[i];
            if (currentApparatus.fireDept != null) {
                var fireDept = currentApparatus.fireDept;
                var apparatusTypeAndNumber = currentApparatus.apparatusTypeAndNumber;

                var apparatusInfo = 
                "<li>" + fireDept + 
                " " + 
                apparatusTypeAndNumber + 
                "<span>" +
                    `<div class='delButton' id='${currentLocation}'>X</div>` +
                `</span><div class='editHoses' id='${currentHose}'>` +
                `Edit Hoses for ${fireDept} ${apparatusTypeAndNumber}` + "</div></li>";
                document.getElementById('theDisplayArea').innerHTML += apparatusInfo;
                currentLocations.push(currentLocation);
                currentHoses.push(currentHose);

                currentLocation = "currentLocation";   //reset variable for the next loop
                currentHose = "currentHose";  //reset variable for the next loop;
            }
        }

        for (var i=0; i < apparatusList.length; i++) {
            var currentLocation = currentLocations[i];
            var currentHose = currentHoses[i];
            var fireDept = apparatusList[i].fireDept;
            var apparatusTypeAndNumber = apparatusList[i].apparatusTypeAndNumber;

            this.createDeleteApparatusButton(currentLocation, apparatusTypeAndNumber);
            this.createEditHosesButton(currentHose, fireDept, apparatusTypeAndNumber);
        }
    }

    createDeleteApparatusButton(currentLocation, apparatusTypeAndNumber) {
        const button = document.getElementById(currentLocation);
        button.classList.add('button');
        button.classList.add(currentLocation);

        button.addEventListener('click', async () => {
            if (confirm("Click OK to delete this apparatus.") == true) {
            await this.client.deleteApparatus(apparatusTypeAndNumber);
            await this.displayApparatus();  //reload the page
            }
        });
        return button;
    }



    displayAddApparatusMenu() {
        (document.getElementById('addApparatusForm').innerHTML += "<form class='addAppForm' id='addAppForm'>" +
            "<label for='fireDept'>Add an apparatus: </label>" +
            "<input type='text' id='fireDept' placeHolder='Fire Department' style='width: 200px' required>" +
            "<input type='text' id='apparatusTypeAndNumber' placeHolder='Apparatus Type and Number' style='width: 200px' required>" +
            "<input type='submit' value='Add Apparatus'></div>"
        );
         this.addApparatusFormSubmitter();
    }

    addApparatusFormSubmitter() {
        var addApparatusForm = document.getElementById('addAppForm');
        addApparatusForm.addEventListener('submit', async (event) => {
            event.preventDefault()  //prevents auto-submit

            var inputFireDept = document.getElementById('fireDept').value;
            var inputApparatusTypeAndNumber = document.getElementById('apparatusTypeAndNumber').value;

            await this.client.addApparatus(inputFireDept, inputApparatusTypeAndNumber);

            await this.displayApparatus();
        });
    }

    createEditHosesButton(currentHose, fireDept, apparatusTypeAndNumber) {
        const button = document.getElementById(currentHose);
        button.classList.add('button');
        button.classList.add(currentHose);

        button.addEventListener('click', async () => {
            if (confirm("Click OK to edit the hoses this apparatus.") == true) {
            await this.editHoses.displayHoses(fireDept, apparatusTypeAndNumber);
            }
        });
        return button;
    }

}
