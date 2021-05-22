import componentComponentDetail from '../components/componentComponentDetail.js'


export default {
    name: 'Component detail',
    components: {componentComponentDetail},

    props: {
        pageParams: Object,
    },

    methods: {
        onGoBack() {
            this.$emit('go-to-component-list');
        },
    },

    mounted() {
        console.log(this.pageParams);
    },

    template: `
        <div>
         <div class="content">
            <div class="container-fluid">
              <div class="row">
                <div class="col-md-4">
                  <componentComponentDetail :id="pageParams.id"></componentComponentDetail>
                </div>
              </div>
            </div>
          </div>
        </div>
    `,
};