import componentCarDetail from '../components/componentCarDetail.js'
// import store from "../../store";

export default {
    name: 'Car detail',
    components: {componentCarDetail},

    props: {
        pageParams: Object,
    },

    setup() {
        const title = 'Car detail'

        return {title}
    },

    mounted() {
        console.log(this.pageParams);
    },

    template: `
        <div>
            {{ title }} {{id}} {{pageParams.id}}
            <componentCarDetail :id="pageParams.id"></componentCarDetail>
        </div>
    `,
};