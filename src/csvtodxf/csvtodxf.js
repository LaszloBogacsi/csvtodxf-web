import React, {Component} from 'react'
import InputData from "../input-data/InputData";

class CsvToDxf extends Component {
    render() {
        return (
            <div>
                <h2>CSV to DXF converter</h2>
                <h4>Convert csv survey data to Cad drawing</h4>
                <InputData/>
            </div>
        );

    }
}

export default CsvToDxf;