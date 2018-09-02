import React, {Component} from 'react'
import * as Rx from "rxjs-compat";

class Fileupload extends Component {

    constructor(props) {
        super(props);
        this.state = {
            value: ''
        };

        this.handleChange = this.handleChange.bind(this);
    }

    handleChange(event) {
        const fileName = event.target.files[0].name;
        this.setState({value: fileName});
        this.handleSubmit(event, fileName);
    }

    handleSubmit(e, fileName) {
        this.props.onFileNameChange(fileName);
        const file = e.target.files[0];
        let data = new FormData();
        data.append("file", file);
        const url = 'http://localhost:9090/upload-file';
        Rx.Observable.fromPromise(fetch(url, {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
            },
            body: data
        })).flatMap(res => res.json())
            .subscribe(response => {
                this.props.onDrawingIdChange(response.id);
                console.log(response);
                },
                error => console.log(error)
            )
    }

    render() {
        return (
            <form>
                <label>Select a csv file form your computer</label>
                <input type="file" name="upload" accept=".csv, .txt"  onChange={this.handleChange}/>
            </form>
        );

    }
}

export default Fileupload;