import axios from "axios";
import BindingClass from "../util/bindingClass";
import Authenticator from "./authenticator";

/**
 * Client to call the MusicPlaylistService.
 *
 * This could be a great place to explore Mixins. Currently the client is being loaded multiple times on each page,
 * which we could avoid using inheritance or Mixins.
 * https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Classes#Mix-ins
 * https://javascript.info/mixins
  */
export default class MusicPlaylistClient extends BindingClass {

    constructor(props = {}) {
        super();

        const methodsToBind = ['clientLoaded', 'getIdentity', 'login', 'logout', 'getApparatus', 'deleteApparatus', 'addApparatus'];  //originally had 'getPlaylist', 'getPlaylistSongs', 'createPlaylist'
        this.bindClassMethods(methodsToBind, this);

        this.authenticator = new Authenticator();;
        this.props = props;

        axios.defaults.baseURL = process.env.API_BASE_URL;
        this.axiosClient = axios;
        this.clientLoaded();
    }

    /**
     * Run any functions that are supposed to be called once the client has loaded successfully.
     */
    clientLoaded() {
        if (this.props.hasOwnProperty("onReady")) {
            this.props.onReady(this);
        }
    }

    /**
     * Get the identity of the current user
     * @param errorCallback (Optional) A function to execute if the call fails.
     * @returns The user information for the current user.
     */
    async getIdentity(errorCallback) {
        try {
            const isLoggedIn = await this.authenticator.isUserLoggedIn();

            if (!isLoggedIn) {
                return undefined;
            }

            return await this.authenticator.getCurrentUserInfo();
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }

    async login() {
        this.authenticator.login();
    }

    async logout() {
        this.authenticator.logout();
    }

    async getTokenOrThrow(unauthenticatedErrorMessage) {
        const isLoggedIn = await this.authenticator.isUserLoggedIn();
        if (!isLoggedIn) {
            throw new Error(unauthenticatedErrorMessage);
        }

        return await this.authenticator.getUserToken();
    }

    /**
     * Gets the Apparatus for the given user name.
     * @param userName The user name used to select associated apparatus
     * @param errorCallback (Optional) A function to execute if the call fails.
     * @returns The apparatus metadata.
     */
    async getApparatus(errorCallback) {
        try {
            const token = await this.getTokenOrThrow("Only authenticated users can make get apparatus requests.");
            const response = await this.axiosClient.get(`apparatus`, {   
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            return response.data.apparatusModelList;
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }

    async deleteApparatus(apparatusTypeAndNumber, errorCallback) {
        try {
            const token = await this.getTokenOrThrow("Only authenticated users can make delete apparatus requests.");
            const response = await this.axiosClient.delete(`apparatus/` + apparatusTypeAndNumber, {   
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            return response.data.apparatusModelList;
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }

    async addApparatus(fireDept, apparatusTypeAndNumber, errorCallback) {
        console.log("fireDept " + fireDept);
        console.log("apparatusTypeAndNumber " + apparatusTypeAndNumber);

        try {
           const token = await this.getTokenOrThrow("Only authenticated users can add an apparatus.");
           const response = await this.axiosClient.post(`apparatus`, {
                fireDept: fireDept,
                apparatusTypeAndNumber: apparatusTypeAndNumber
            }, {
                headers: {
                   Authorization: `Bearer ${token}`
               }
           });
           console.log("pre-return");
           return response.data.apparatusModelList;
       } catch (error) {
           this.handleError(error, errorCallback)
       }
   }

   async getIndividualApparatus(fireDept, apparatusTypeAndNumber, errorCallback) {
    try {
        const token = await this.getTokenOrThrow("Only authenticated users can make get apparatus requests.");
        console.log("calling axios client with fireDept: " + fireDept + " and apparatusTypeAndNumber: " + apparatusTypeAndNumber);
        const response = await this.axiosClient.get(`apparatus/individual?fireDept=${fireDept}&apparatusTypeAndNumber=${apparatusTypeAndNumber}`, {   
            headers: {
                Authorization: `Bearer ${token}`
            }
        });
        return response.data.apparatusModel;
    } catch (error) {
        this.handleError(error, errorCallback)
    }
}

    async deleteHose(fireDept, apparatusTypeAndNumber, hoseIndexNumber) {
        try {
            const token = await this.getTokenOrThrow("Only authenticated users can make delete hose requests.");
            const response = await this.axiosClient.delete(`apparatus/hose?fireDept=${fireDept}&apparatusTypeAndNumber=${apparatusTypeAndNumber}&hoseIndexNumber=${hoseIndexNumber}`, {   
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            return response.data.apparatusModel;
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }

    async addHose(fireDept, apparatusTypeAndNumber, name, color, length, diameter, gallons) {
        try {
            const token = await this.getTokenOrThrow("Only authenticated users can make add hose requests.");
            const response = await this.axiosClient.put(`apparatus/hose`, {
                fireDept: fireDept,
                apparatusTypeAndNumber: apparatusTypeAndNumber,
                name: name,
                color: color,
                length: length,
                diameter: diameter,
                gallons: gallons
            }, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            return response.data.apparatusModel;
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }

    async getConstants() {
        try {
            const token = await this.getTokenOrThrow("Only authenticated users can make get constants requests.");
            const response = await this.axiosClient.get(`constants`, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            return response.data.constantModelList;
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }

    /**
     * Helper method to log the error and run any error functions.
     * @param error The error received from the server.
     * @param errorCallback (Optional) A function to execute if the call fails.
     */
    handleError(error, errorCallback) {
        console.error(error);

        const errorFromApi = error?.response?.data?.error_message;
        if (errorFromApi) {
            console.error(errorFromApi)
            error.message = errorFromApi;
        }

        if (errorCallback) {
            errorCallback(error);
        }
    }
}
