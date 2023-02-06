const Sweater = ({sweater}) => {
    return (

        <div className="sweater-card">
            <p>Your new sweater is</p>
            <h2 className="sweater-colour">{sweater.orderNumber}</h2>
            <h2 className="sweater-colour">{sweater.colour}</h2>
        </div>
    );
};

export default Sweater;
