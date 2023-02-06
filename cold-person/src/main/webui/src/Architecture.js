import {useEffect, useState} from "react";
import Component from "./Component";
import axios from "axios";


const Architecture = () => {
    const [components, setComponents] = useState([]);

    console.log("HOLLY rendering arch")
    useEffect(() => {
        const fetchArchitectureRecords = async () => {
            console.log("HOLLY will fetch")
            try {
                const res = await axios.get(
                    "http://localhost:8088/recorder/components",
                    {
                        crossDomain: true,
                        headers: {
                            "Content-Type": "application/json",
                        },
                    }
                );

                console.log("HOLLY answer is", res?.data)
                setComponents(res?.data);
            } catch (err) {
                console.error(err.message);
            }
        }

        fetchArchitectureRecords();
    }, [])


    return (
        <>
            <div className="components-container">
                {components?.map((component) => {
                    return (
                        <Component key={component.name} component={component}/>
                    );
                })}
            </div>
        </>
    );
};

export default Architecture;
