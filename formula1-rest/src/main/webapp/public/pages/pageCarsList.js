import componentCarsList from '../components/componentCarsList.js'

export default {
    name: 'Cars',
    icon: 'commute',
    showInMenu: true,

    components: {componentCarsList},

    setup() {        
        const title = 'Cars'
        return {title}
    },

    methods: {
        onShowCarDetail(carId) {
            console.log("onShowCarDetail " + carId);

            this.$emit('show-car-detail', carId);
        }
    },

    template: `
        <div>
            {{ title }}
            <componentCarsList @show-car-detail="onShowCarDetail"></componentCarsList>
        </div>
    `,
};