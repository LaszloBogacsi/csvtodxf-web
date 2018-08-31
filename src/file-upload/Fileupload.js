import React, {Component} from 'react'

class Fileupload extends Component {

    constructor(props) {
        super(props);
        this.state = {value: ''};

        this.handleChange = this.handleChange.bind(this);
    }

    handleChange(event) {
        console.log(event.target.value);
        this.setState({value: event.target.value});
        this.handleSubmit();
    }

    handleSubmit() {
        alert(this.state.value);
    }

    render() {
        return (
            <form>
                <label>Select a csv file form your computer</label>
                <input type="file" name="upload" accept=".csv, .txt" value={this.state.value} onChange={this.handleChange}/>
            </form>
        );

    }
}

export default Fileupload;