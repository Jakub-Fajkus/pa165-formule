import componentDriversList from '../components/componentDriversList.js'

export default {
    name: 'Drivers',
    icon: 'accessible',
    components: {componentDriversList},

    setup() {
        const title = 'Drivers'
        return {title}
    },

    methods: {
        onShowDriverDetail(driverId) {
            console.log("onShowDriverDetail " + driverId);

            this.$emit('show-driver-detail', driverId);
        },

        onNewDriverDetail() {
            console.log("onNewDriverDetail ");

            this.$emit('new-driver-detail');
        }

    },

    template: `
        <div>
            {{ title }}
            <componentDriversList @show-driver-detail="onShowDriverDetail" @new-driver-detail="onNewDriverDetail"></componentDriversList>
        </div>
    `,
};