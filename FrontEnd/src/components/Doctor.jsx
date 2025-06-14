import { useState, useEffect, useRef } from "react";
import axios from "axios";
import { toast } from "sonner";
import { useNavigate, useParams } from "react-router-dom";


const ListOfDoctor = () => {
    const [DoctorList, setDoctorList] = useState([]);
    const Navigate = useNavigate();

    useEffect(() => {
        fetchData();
    }, [])

    const fetchData = async () => {
        try {
            let response = await axios.get("http://localhost:3000/doctor", {
                headers: {
                    "Content-type": "Application/json",
                    Authorization: `Bearer ${localStorage.getItem("token")}`
                }
            })
            setDoctorList(response.data)
        } catch (error) {
            console.log("List Of Doctor " + error);
        }
    }

    const handleUpdate = async (id) => {
        Navigate(`/doctor/modifer/${id}`);
    }

    const handleDelete = async (id) => {
        try {
            let response = await axios.delete(`http://localhost:3000/doctor/delete/${id}`, {
                headers: {
                    "Content-type": "Application/json",
                    Authorization: `Bearer ${localStorage.getItem("token")}`
                }
            })

            if (response.status === 200) {
                toast.success("Deleted successfully!")
                fetchData();
            }

        } catch (error) {
            console.log("List Of Doctor " + error);
        }
    }

    const MyArr = [];
    let length = DoctorList?.length ?? 0;
    for (let i = 0; i < length; i++) {
        const element = DoctorList[i];
        MyArr.push(<tr key={i}>
            <td>{i + 1}</td>
            <td>{element.doctor_id}</td>
            <td>{element.doctor_name + " " + element.doctor_education}</td>
            <td>{element.doctor_specializedfield}</td>
            <td>
                <button className="listofpatient-table-bookBn" onClick={() => { handleUpdate(element.doctor_id) }}>Update</button>
                <button className="listofpatient-table-bookBn" onClick={() => { handleDelete(element.doctor_id) }}>Delete</button>
            </td>
        </tr>)
    }

    return (
        <div className="listofpatient-Container">
            <div className="listofpatient-table">
                <h3 className="listofpatient-table-title">Doctor List</h3>
                <table border={1}>
                    <thead>
                        <tr>
                            <th>S No</th>
                            <th>Doctor ID</th>
                            <th>Doctor Name</th>
                            <th>Doctor Specialize</th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        {MyArr.length > 0 ? MyArr :
                            <tr key="temp">
                                <td colSpan={5} className="medicine-error-massage">No Data</td>
                            </tr>}
                    </tbody>
                </table>
            </div>
        </div>
    )
}

const DoctorModifer = () => {

    const { update_id } = useParams();
    const Navigate = useNavigate();

    const [FormData, setFormData] = useState({
        doctor_id: "",
        doctor_name: "",
        doctor_education: "",
        doctor_specializedfield: "",
    });
    const DoctorForm = useRef(null);
    const [Username, setUsername] = useState('');
    const [Password, setPassword] = useState('');
    const [ConfirmPassword, setConfirmPassword] = useState('');

    useEffect(() => {
        if (update_id) {
            LoadOldData(update_id);
        }
    }, []);


    const LoadOldData = async (update_id) => {

        try {

            let response = await axios.get(`http://localhost:3000/doctor/id/${update_id}`, {
                headers: {
                    "Content-type": "Application/json",
                    Authorization: `Bearer ${localStorage.getItem("token")}`
                }
            })

            setFormData({
                doctor_id: response.data.doctor_id,
                doctor_name: response.data.doctor_name,
                doctor_education: response.data.doctor_education,
                doctor_specializedfield: response.data.doctor_specializedfield,
            });

        } catch (error) {
            console.log("Doctor " + error);
        }
    }

    const handleUpdate = async (e) => {
        e.preventDefault();

        const hasErrors = Object.keys(FormData).some((key) => FormData[key] === "");
        if (hasErrors) {
            toast.error("Please fill the form correctly");
            return;
        }

        try {

            let response = await axios.put(`http://localhost:3000/doctor/update/${update_id}`, FormData, {
                headers: {
                    "Content-type": "Application/json",
                    Authorization: `Bearer ${localStorage.getItem("token")}`
                }
            })

            if (response.status === 200) {
                toast.success("Doctor details updated successfully!");
                resetTheForm();
                Navigate("/doctor/list");
            }

        } catch (error) {
            console.log("Doctor " + error);
        }
    }

    const handleChanges = (event) => {
        const { name, value } = event.target;

        setFormData((prevData) => ({
            ...prevData,
            [name]: value,
            doctor_id: "D" + Math.floor(1000 + Math.random() * 9000)
        }));

    };

    const handleSubmit = async (event) => {
        event.preventDefault();

        const hasErrors = Object.keys(FormData).some((key) => FormData[key] === "");
        if (hasErrors) {
            toast.error("Please fill the form correctly");
            return;
        }

        if (!Password || !ConfirmPassword) {
            toast.error("Please enter password");
            return;
        } else if (Password != ConfirmPassword) {
            toast.error("Password mismatch, please update correctly");
            return;
        }

        let registerData = {
            userid: FormData.doctor_id,
            username: Username,
            password: ConfirmPassword,
            user_role: "ROLE_DOCTOR"
        }

        // console.log(FormData);
        // console.log(registerData);

        try {

            let personalresponse = await axios.post("http://localhost:3000/doctor/add", FormData, {
                headers: {
                    "Content-type": "Application/json",
                    Authorization: `Bearer ${localStorage.getItem("token")}`
                }
            })

            let registerresponse = await axios.post("http://localhost:3000/user/register", registerData, {
                headers: {
                    "Content-type": "Application/json",
                    Authorization: `Bearer ${localStorage.getItem("token")}`
                }
            })

            if (personalresponse.status === 201) {
                toast.success("Doctor added successfully!")
                if (registerresponse.status === 201) {
                    toast.success("New doctor registered successfully!")
                    resetTheForm();
                }
            }

        } catch (error) {
            console.log("Doctor " + error);
        }


    }

    const resetTheForm = () => {
        DoctorForm.current.reset();
        setFormData({
            doctor_id: "",
            doctor_name: "",
            doctor_education: "",
            doctor_specializedfield: "",
        });
        setUsername("");
        setPassword("");
        setConfirmPassword("");
    }



    return (
        <div className="patientmodifer-container">
            <h2 className="patientmodifer-container-title">New Doctor Registation</h2>
            <div className="patientmodifer-formcontainer">
                <form ref={DoctorForm}>
                    <div className="patientmodifer-formcontainer-seperate">
                        <div className="patientmodifer-formcontainer-personaldetails">
                            <h3>Doctor Information</h3>
                            <div className="patientmodifer-formcontainer-field">
                                <label htmlFor="name">Name:</label>
                                <input type="text" name="doctor_name" id="name" required autoComplete="off"
                                    value={FormData.doctor_name} onChange={handleChanges}
                                />
                            </div>
                            <div className="patientmodifer-formcontainer-field">
                                <label htmlFor="age">Education:</label>
                                <input type="text" name="doctor_education" id="age" required
                                    value={FormData.doctor_education} onChange={handleChanges}
                                />
                            </div>
                            <div className="patientmodifer-formcontainer-field">
                                <label htmlFor="place">Specialize on:</label>
                                <input type="text" name="doctor_specializedfield" id="place" required
                                    value={FormData.doctor_specializedfield} onChange={handleChanges}
                                />
                            </div>
                        </div>
                        {!update_id &&
                            <div className="patientmodifer-formcontainer-logindetails">
                                <h3>Generate Password</h3>
                                <div className="patientmodifer-formcontainer-field">
                                    <label htmlFor="username">Username:</label>
                                    <input type="text" name="username" id="username" required
                                        value={Username} onChange={(e) => setUsername(e.target.value)}
                                    />
                                </div>
                                <div className="patientmodifer-formcontainer-field">
                                    <label htmlFor="password">New Password:</label>
                                    <input type="password" name="password" id="password" required
                                        value={Password} onChange={(e) => setPassword(e.target.value)}
                                    />
                                </div>
                                <div className="patientmodifer-formcontainer-field">
                                    <label htmlFor="confirmpassword">Confirm Password:</label>
                                    <input type="password" name="confirmpassword" id="confirmpassword" required
                                        value={ConfirmPassword} onChange={(e) => setConfirmPassword(e.target.value)}
                                    />
                                </div>
                            </div>}
                    </div>
                    <div className="patientmodifer-formcontainer-field">
                        <div></div>
                        {!update_id ?
                            (<button className="header-bns" onClick={(e) => { handleSubmit(e) }}>Submit</button>)
                            :
                            (<button className="header-bns" onClick={(e) => { handleUpdate(e) }}>Update</button>)
                        }
                    </div>
                </form>
            </div>
        </div >
    )
}

export { ListOfDoctor, DoctorModifer };