@param String org_name
@param String org_id
@param String last_month_iso
@param String today_iso

<link rel="stylesheet" href="main.css">

<body class="container_success">
<div class="logged_in_div_left flex_column">
  <div class="flex logged_in_div_left_title">
    <div><h2>${org_name}</h2></div>
  </div>
  <hr style="height:0.5px;width:75%;">
  <div class="flex flex_column success-buttons">
    <a href="/logout">
      <div class="flex sidebar-button">
        <div><img class="org-icon"  src="/images/icon-settings.svg" alt="settings icon"></div>
        <div>
          <p>Organizations</p>
        </div>
      </div>
    </a>
    <div class="flex sidebar-button selected">
      <div><img class="org-icon"  src="/images/icon-document.svg" alt="edit icon"></div>
      <div>
        <p>Audit Logs</p>
      </div>
    </div>
  </div>
</div>

<div class="logged_in_div_right">
  <div class="logged_in_nav margin-y-15">
    <div class="flex">
      <div>
        <a href="https://workos.com/docs" target="_blank"><button class='button nav-item'>Documentation</button></a>
      </div>
      <div>
        <a href="https://workos.com/docs/reference" target="_blank"><button class='button nav-item'>API
            Reference</button></a>
      </div>
      <div>
        <a href="https://workos.com/blog" target="_blank"><button
                  class='button nav-item blog-nav-button'>Blog</button></a>
      </div>
      <div>
        <a href="https://workos.com/" target="_blank">
          <img class="nav-image" src="/images/workos_favicon.png" alt="link to workos.com">
        </a>
      </div>
    </div>
  </div>
  <div class="flex_column audit-logs-card width-65vw logged_in_right_content">
    <div class="flex flex-start width-70vw ">
      <div><h2>Audit Logs</h2></div>
    </div>
    <div class="flex card width-65vw space-between flex-row">
      <div>
        <code class="org-id">${org_id}</code>
      </div>
      <div>
        <a href="/logout"><button class="button">Change Org</button></a>
      </div>
    </div>
    <div class="card events-card width-65vw height-45vh">
      <div class="flex flex-start">
        <div class="flex-column flex-start">
          <div data-tab-target="#send-events" name='#send-events' class="flex space-evenly width-11vw content-button tab">
            <div><img class="org-icon"  src="/images/icon-send.svg" alt="send icon"></div>
            <div>
              <a href="#send-events" class="remove-style"><button class="remove-style">Send Events</button></a>
            </div>
          </div>
          <div data-tab-target="#export-events" name='#export-events' class="flex space-evenly width-11vw content-button  tab">
            <div><img class="org-icon"  src="/images/icon-download.svg" alt="download icon"></div>
            <div>
              <a href="#export-events" class="remove-style"><button class="remove-style">Export Events</button></a>
            </div>
          </div>
          <div class="flex space-evenly width-11vw content-button tab">
            <div><img class="org-icon"  src="/images/icon-eye.svg" alt="eye icon"></div>
            <div>
              <a href="/admin_portal?intent=audit_logs" class="remove-style"><button class="remove-style">View Events</button></a>
            </div>
          </div>
          <div class="flex space-evenly width-11vw content-button tab">
            <div><img class="org-icon"  src="/images/icon-share2.svg" alt=""></div>
            <div>
              <a href="/admin_portal?intent=log_streams" class="remove-style"><button class="remove-style">Configure Log Streams</button></a>
            </div>
          </div>
        </div>
        <div class="tab-content">
          <div id="send-events" name='#send-events' data-tab-content class="flex_column">
            <div>
              <h3>Send Events</h3>
              <p>Configure and send a "User Organization Deleted" Event</p>
            </div>
            <hr style="height:0.5px;width:100%;margin-bottom:25px;">
            <form action="/send_event" method="POST">
              <div class="event-form-input-group flex-column flex-start">
                <div><label for="event-version">Event Version</label></div>
                <div><input required name="event-version" class="text-input" type="text" placeholder="[Integer] schema version of the event"></div>
              </div>
              <div class="event-form-input-group flex-column flex-start">
                <div><label for="actor-name">Actor Name</label></div>
                <div><input required name="actor-name" class="text-input" type="text" placeholder="Name of the user who completed the action"></div>
              </div>
              <div class="event-form-input-group flex-column flex-start">
                <div><label for="actor-type">Actor Type</label></div>
                <div><input required name="actor-type" class="text-input" type="text" placeholder="Type of the user who completed the action"></div>
              </div>
              <div class="event-form-input-group flex-column flex-start">
                <div><label for="target-name">Target Name</label></div>
                <div><input required name="target-name" class="text-input" type="text" placeholder="Name of the target"></div>
              </div>
              <div class="event-form-input-group flex-column flex-start">
                <div><label for="target-type">Target Type</label></div>
                <div><input required name="target-type" class="text-input" type="text" placeholder="Defined in WorkOS Dashboard"></div>
              </div>
              <div>
                <button class="button button-outline" name="event" id="user_org_deleted" value="user-organization-deleted" type="submit" onclick="showSnackbar()">
                  <div class="flex width-100p">
                    <p>Send Event</p>
                  </div>
                </button>
              </div>
            </form>
          </div>

          <div id="export-events" name='#export-events' data-tab-content>
            <form action="/get_events" method="POST">
              <h3>Export Events</h3>
              <div>
                <p>Audit Log events can be exported in a 2 step process. First, generate a CSV, and then Download it.</p>
              </div>
              <hr style="height:0.5px;width:100%;margin-bottom:25px;">
              <div class="flex-column">
                <div class="event-form-input-group flex-column flex-start">
                  <div><label for="range-start">Range Start</label></div>
                  <div><input required name="range-start" class="text-input" type="text" value="${last_month_iso}" placeholder="ISO-8601 value for start of the export range"></div>
                </div>
                <div class="event-form-input-group flex-column flex-start">
                  <div><label for="range-end">Range End</label></div>
                  <div><input required name="range-end" class="text-input" type="text" value="${today_iso}" placeholder="ISO-8601 value for end of the export range"></div>
                </div>
                <div class="event-form-input-group flex-column flex-start">
                  <div><label for="filter-actions">Actions</label></div>
                  <div><input  name="filter-actions" class="text-input" type="text" placeholder="Optional list of actions to filter on"></div>
                </div>
                <div class="event-form-input-group flex-column flex-start">
                  <div><label for="filter-actors">Actors</label></div>
                  <div><input  name="filter-actors" class="text-input" type="text" placeholder="Optional list of actors to filter on"></div>
                </div>
                <div class="event-form-input-group flex-column flex-start">
                  <div><label for="filter-targets">Targets</label></div>
                  <div><input  name="filter-targets" class="text-input" type="text" placeholder="Optional list of targets to filter on"></div>
                </div>
              </div>
              <div class="flex flex-start">
                <div class="flex width-150px">
                  <button class="button button-outline" name="event" id="generate_csv" value="0" type="submit">Generate CSV</button>
                </div>
                <div class="flex width-150px">
                  <button class="button button-outline" name="event" id="access_csv" value="1" type="submit">Download CSV</button>
                </div>
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>

    <div id="snackbar">Log Event Sent</div>
  </div>
</div>

<script src="https://cdn.jsdelivr.net/gh/google/code-prettify@master/loader/run_prettify.js"></script>
<script>
        function showSnackbar() {
            var x = document.getElementById("snackbar");
            x.className = "show";
            setTimeout(function(){ x.className = x.className.replace("show", ""); }, 500);
        }

        const tabs = document.querySelectorAll('[data-tab-target]')
        const tabContents = document.querySelectorAll('[data-tab-content]')

        tabs.forEach(tab => {
            tab.addEventListener('click', () => {
                const target = document.querySelector(tab.dataset.tabTarget)
                tabContents.forEach(tabContent => {
                    tabContent.classList.remove('active')
                })
                tabs.forEach(tab => {
                    tab.classList.remove('active')
                })
                tab.classList.add('active')
                target.classList.add('active')
                localStorage.setItem('selectedTab', target.getAttribute('name'))
            })
        })

        tabs.forEach(tab => {
            if(localStorage.getItem('selectedTab') != undefined || null) {
                if(tab.getAttribute('name') == localStorage.getItem('selectedTab')){
                    const target = document.querySelector(tab.dataset.tabTarget)
                    tabContents.forEach(tabContent => {
                        tabContent.classList.remove('active')
                    })
                    tabs.forEach(tab => {
                        tab.classList.remove('active')
                    })
                    tab.classList.add('active')
                    target.classList.add('active')
                }
            }
        })
    </script>
</body>