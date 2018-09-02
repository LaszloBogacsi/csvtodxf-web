import React, {Component} from 'react'
import InputData from "./input-data/InputData";
import Result from "./results/Result";

class CsvToDxf extends Component {
    constructor(props) {
        super(props);
        this.handleConvertResponse = this.handleConvertResponse.bind(this);

        this.state = {
            convertResponse: ''
        }
    }

    handleConvertResponse(response) {
        this.setState({convertResponse: response})
    }

    render() {
        const convertResponse = this.state.convertResponse;
        return (
            <div>
                <h2>CSV to DXF converter</h2>
                <h4>Convert csv survey data to Cad drawing</h4>
                <InputData onConvertResponse={this.handleConvertResponse}/>
                <Result convertResponse={convertResponse}/>
            </div>
        );

    }
}

export default CsvToDxf;