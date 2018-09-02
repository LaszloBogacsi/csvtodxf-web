import React, {Component} from 'react'

class Download extends Component {
    constructor(props) {
        super(props);
        this.handleOnclick = this.handleOnclick.bind(this);
    }

    handleOnclick() {
        const id = this.props.downloadId;
        const url = `http://localhost:9090/download/${id}`;
        window.location.href = url;
    }


    render() {
        // const id = this.props.downloadId;
        return (
            <div>
                {/*<a href={`http://localhost:9090/download/${id}`}>Download</a>*/}
                <button onClick={this.handleOnclick}>Download</button>
            </div>
        );

    }
}

export default Download;