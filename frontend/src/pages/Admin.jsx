import EmployeeTable from "../tables/EmployeeTable.jsx";
import EditTable from "../tables/EditTable.jsx";

const Admin = () => {
    return (
        <div>
            <h2>Employee table</h2>
            <EmployeeTable/>
            <h2>Edit table</h2>
            <EditTable/>
        </div>
    );
};

export default Admin;