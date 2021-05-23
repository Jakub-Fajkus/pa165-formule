import componentUsersList from '../components/componentUsersList.js'

/**
 * @author Jiri Andrlik
 */
export default {
    name: 'Users',
    icon: 'accessibility',
    showInMenu: true,
    requiredRoles: ["ROLE_MANAGER"],

    components: {componentUsersList},

    setup() {
        const title = 'Users'
        return {title}
    },

    methods: {
        onShowUserDetail(userId) {
            console.log("onShowUserDetail " + userId);

            this.$emit('show-user-detail', userId);
        },

        onNewUserDetail() {
            console.log("onNewUserDetail");

            this.$emit('new-user-detail');
        },
    },

    template: `
        <div>
            <componentUsersList @show-user-detail="onShowUserDetail" @new-user-detail="onNewUserDetail" @show-users-list="onShowUsersList"></componentUsersList>
        </div>
    `,
};