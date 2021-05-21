export default {

    //get url parameter (used in Page2)
    getUrlParam(key) {
        var searchParams = new URLSearchParams(window.location.search);
        return searchParams.get(key);
    },

    
    //sorting array of objects (used in Component3)
    sort(key, order = 'asc') {
        return function compare(a,b) {
            let comparison = 0;
            const varA = (typeof a[key] === 'string') ? a[key].toUpperCase() : a[key];
            const varB = (typeof b[key] === 'string') ? b[key].toUpperCase() : b[key];
            if ( varA < varB ) comparison = -1; if ( varA > varB ) comparison = 1; 
            return ((order === 'desc') ? (comparison * -1) : comparison);
        };
    },

    showNotification(type, message, icon) {
        // md.showNotification('top','right')
        $.notify({
            icon: icon,
            message: message

        }, {
            type: type,
            timer: 5000,
            placement: {
                from: 'top',
                align: 'right'
            }
        });
    },

    showErrorNotification(message) {
        this.showNotification("danger", message, "add_alert");
    },

    showSuccessNotification(message) {
        this.showNotification("success", message, "add_alert");
    },

    showWarningNotification(message) {
        this.showNotification("warning", message, "add_alert");
    }
    

}