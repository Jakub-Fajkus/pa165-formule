import componentComponentsList from "../components/componentComponentsList.js";

export default {
    name: 'Component',
    components: {componentComponentsList},
    icon: 'handyman',

    setup() {
        const title = ''
        return {title}
    },

    methods: {
        onShowComponentDetail(componentId) {
            console.log("onShowComponentDetail " + componentId);

            this.$emit('show-component-detail', componentId);
        }


    },

    template: `
        <div class="row">
            <div class="col-md-12">
                <button @click="$emit('add-component')" class="btn btn-primary pull-right">+</button>
            </div>
        </div>
                
        <div>
            {{ title }}
            <componentComponentsList @show-component-detail="onShowComponentDetail"></componentComponentsList>
            
        </div>
    `,
};