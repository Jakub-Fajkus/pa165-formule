import componentComponentsList from "../components/componentComponentsList.js";

export default {
    name: 'Component',
    components: {componentComponentsList},
    icon: 'handyman',
    showInMenu: true,

    setup() {
        const title = ''
        return {title}
    },

    methods: {
        onShowComponentDetail(componentId) {
            console.log("onShowComponentDetail " + componentId);
            this.$emit('show-component-detail', componentId);
        },

        onAddComponent() {
            console.log("onAddComponent");
            this.$emit('add-component');
        }
    },

    template: `     
        <div>
            {{ title }}
            <componentComponentsList @show-component-detail="onShowComponentDetail" @add-component="onAddComponent"></componentComponentsList>
        </div>
    `,
};