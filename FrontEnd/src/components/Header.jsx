import { useNavigate } from "react-router-dom";
import { useState, useEffect } from "react";

const Header = () => {
    const navigate = useNavigate();

    const token = localStorage.getItem("token");

    const handleLogout = () => {
        localStorage.removeItem("token");
        localStorage.removeItem("role");
        navigate("/");
    };

    return (
        <div className="header-container">
            <div>
                <img className="logo"
                    src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAADIAAAAyCAYAAAAeP4ixAAAACXBIWXMAAAsTAAALEwEAmpwYAAAC1ElEQVR4nO2YX4hMURzHP7YYQwjFopR9oCztA8ubVngR5W9bvPBC/iue9mG6+7K7aou3tR6swhMJg0hR9sGTQpZSSykjxbzYlvJndOo7uZ1mduf+2Xvv6H7q19x7fnd+53znd+6c3zmQkpKSMgazgCGgBHwBWvHPWuCrYr0CZhIh69TxRuA5kAsQK6cYJlZJsSOjTZ0aHgNOgFiOYhhKih06q4DbwFtg2GUFS0jR8nuxoiWkYPlN37c0Fl+sAH4AV4CDwAGX9VpC8pbfi+UtIb2W3/R9VWNp9iOkH7iboKl1Dzjvp4NHQGcV30p1+lBT4yj+OaYYJlZJM6ESnRqTZ8b7pXcCPRpIBv+Y7x5XrB01Zs4TQadM2DipEIL9G4Vt+SAZCbI+hG3FdGqRvuwTgpNOLbxPrelAC7AUmFTB3wAs0zPTkpiRDHAWGFWZYey9Vv8yu9VW9o+qQMwkRUiDistPwB6gEVis2ugnsA/Yr2tHPvPMXn3nTpXsRS5kF/AdWF7Bd1g+Y4cq+JtVnrszF5sQs1e45rpfAGy3tgPGqnEduJwEIfeBM657I+rXGOW4TY9ijMeECzkHdOvaHBz8Bl5oI1QLN4G+JK0jDcAz4JKyYbKySb71+tfaYr3YrXquLWohU6zP2UCT7ATwDVgo3wUd85hDgz8q/IZc51bzgTfAjRrH44QlZAMwqOtB3X92rQvGOlzPN0pYocLefxHwGniqw75IhRhmWJ/zXBlZouk12ZWVnASu4R/m+iPwxOOpohN1rbVVq3Y7MBXY7PK1a0256GOf78RRNJ7WS+yeah1qO1Vv1e82YAQYkI2orS7L+Bbgg1741fW+H5kro96FhIWTCuE/zEiTDK3ocbQ5YQg5IsuqPMlG3BZIyAOgi+TQpTF5plv7ClNmxE0WeOn3h52j0vudVub+mGxApy/DGpMvTHV6Uju4uIT0aQzlijslJQXv/AVPrlcPWfHbfQAAAABJRU5ErkJggg=="
                    alt="health-calendar" />
            </div>
            <p className="header-title">Patient Medicine and Appointment System</p>

            {token ? (
                <div>
                    <button className="header-bns registerBn" onClick={handleLogout}>
                        Logout
                    </button>
                </div>
            ) : (
                <div>
                    <button className="header-bns loginBn" onClick={() => navigate("/")}>
                        Login
                    </button>
                    <button className="header-bns registerBn" onClick={() => navigate("/patient/modifer")}>
                        Register
                    </button>
                </div>
            )}
        </div>
    );
};

export default Header;

